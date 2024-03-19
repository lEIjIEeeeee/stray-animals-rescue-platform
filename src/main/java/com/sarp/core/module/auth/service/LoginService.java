package com.sarp.core.module.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarp.core.context.Context;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.context.HttpContext;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.constant.AuthConstants;
import com.sarp.core.module.auth.dao.UserAuthMapper;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.auth.manager.UserManager;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.auth.model.dto.RegisterReturnDTO;
import com.sarp.core.module.auth.model.dto.SysTokenLoginDTO;
import com.sarp.core.module.auth.model.entity.UserAuth;
import com.sarp.core.module.auth.model.request.LoginRequest;
import com.sarp.core.module.auth.model.request.RegisterRequest;
import com.sarp.core.module.auth.util.AuthNoGenerateUtils;
import com.sarp.core.module.common.enums.GenderEnum;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.enums.UserStatusEnum;
import com.sarp.core.module.user.enums.UserTypeEnum;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.module.user.model.entity.User;
import com.sarp.core.security.jwt.JwtPayLoad;
import com.sarp.core.security.jwt.JwtTokenUtils;
import com.sarp.core.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/1/26 15:41
 */

@Service
@Slf4j
@AllArgsConstructor
public class LoginService {

    private UserMapper userMapper;
    private MemberMapper memberMapper;
    private UserAuthMapper userAuthMapper;
    private ObjectMapper objectMapper;

    private UserManager userManager;
    private MemberManager memberManager;

    @Transactional(rollbackFor = Exception.class)
    public RegisterReturnDTO register(RegisterRequest request) {
        User userByPhone = userManager.getUserByPhone(request.getPhone());
        if (ObjectUtil.isNotNull(userByPhone)) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "手机号已被注册");
        }

        String salt = AuthNoGenerateUtils.generateSalt();
        User user = User.builder()
                        .account(generateAccount())
                        .password(encryptPwd(request.getPassword(), salt))
                        .salt(salt)
                        .phone(request.getPhone())
                        .name(request.getNickName())
                        .gender(GenderEnum.F.name())
                        .userType(UserTypeEnum.NORMAL_USER.name())
                        .status(UserStatusEnum.NORMAL.getCode())
                        .createTime(DateUtil.date())
                        .updateTime(DateUtil.date())
                        .build();
        userMapper.insert(user);

        Member member = Member.builder()
                              .id(user.getId())
                              .account(user.getAccount())
                              .nickName(request.getNickName())
                              .realName(request.getRealName())
                              .phone(request.getPhone())
                              .gender(GenderEnum.F.name())
                              .status(UserStatusEnum.NORMAL.getCode())
                              .build();
        memberMapper.insert(member);

        return RegisterReturnDTO.builder()
                                .nickName(user.getName())
                                .account(user.getAccount())
                                .build();
    }

    public String generateAccount() {
        String account = AuthNoGenerateUtils.generateAccount();
        List<Member> memberList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class));
        if (CollUtil.isNotEmpty(memberList)) {
            Set<String> accountSet = memberList.stream()
                                               .map(Member::getAccount)
                                               .collect(Collectors.toSet());
            while (accountSet.contains(account)) {
                account = AuthNoGenerateUtils.generateAccount();
            }
        }
        return account;
    }

    public static String encryptPwd(String password, String salt) {
        if (StrUtil.isBlank(password)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "密码不能为空!");
        }
        if (StrUtil.isBlank(salt)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "密码盐不能为空!");
        }
        return SecureUtil.md5(password + salt);
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginUser login(LoginRequest request) {
        User user = userManager.getUserByAccount(request.getAccount());
        if (ObjectUtil.isNull(user)) {
            user = userManager.getUserByPhone(request.getAccount());
        }

        if (ObjectUtil.isNull(user)) {
            throw new BizException(HttpResultCode.USER_ACCOUNT_NOT_EXIST);
        }

        checkUserStatus(user);

        String encrypt = encryptPwd(request.getPassword(), user.getSalt());
        if (ObjectUtil.notEqual(encrypt, user.getPassword())) {
            throw new BizException(HttpResultCode.USER_ACCOUNT_OR_PASSWORD_VALIDATE_FAILED);
        }
        LoginUser loginUser = JavaBeanUtils.map(user, LoginUser.class);
        fillLoginUserInfo(loginUser);

        //token生成
        String token;
        UserAuth userAuth = getUserAuth(loginUser.getId());
        if (ObjectUtil.isNotNull(userAuth)) {
            token = userAuth.getToken();
            if (ObjectUtil.isNull(token)
                    || JwtTokenUtils.isTokenExpired(token)) {
                token = generateToken(loginUser);
            }
        } else {
            token = generateToken(loginUser);
        }
        loginUser.setToken(token);

        //保存token到auth表
        updateOrInsertUserAuth(loginUser);

        //添加token到cookie
        addLoginCookie(token);

        //设置上下文信息
        setContext(loginUser);

        return loginUser;
    }

    private void checkUserStatus(User user) {
        if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.USER_STATUS_ERROR, "用户已被冻结");
        }
        if (UserStatusEnum.DELETE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.USER_STATUS_ERROR, "用户已被删除");
        }
    }

    private void fillLoginUserInfo(LoginUser loginUser) {
        Member member = memberManager.getByIdWithExp(loginUser.getId());
        loginUser.setNickName(member.getNickName());
        loginUser.setRealName(member.getRealName());
    }

    private String generateToken(LoginUser loginUser) {
        JwtPayLoad jwtPayLoad = JwtPayLoad.builder()
                                          .userId(loginUser.getId())
                                          .account(loginUser.getAccount())
                                          .phone(loginUser.getPhone())
                                          .build();
        return JwtTokenUtils.generateToken(jwtPayLoad);
    }

    private void updateUserLastLoginTime(UserAuth userAuth) {
        memberMapper.update(Member.builder()
                                  .build(), Wrappers.lambdaUpdate(Member.class)
                                                    .set(Member::getLastLoginTime, userAuth.getCreateTime())
                                                    .eq(Member::getId, userAuth.getUserId()));
    }

    private void updateOrInsertUserAuth(LoginUser loginUser) {
        UserAuth userAuth = getUserAuth(loginUser.getId());
        if (ObjectUtil.isNotNull(userAuth)) {
            userAuthMapper.deleteById(userAuth.getId());
        }

        UserAuth userAuthNew = UserAuth.builder()
                                       .userId(loginUser.getId())
                                       .token(loginUser.getToken())
                                       .key(AuthConstants.LOGIN_USER + loginUser.getId())
                                       .value(objectMapper.convertValue(loginUser, JsonNode.class))
                                       .createTime(DateUtil.date())
                                       .build();
        userAuthMapper.insert(userAuthNew);
        //更新用户最近登录时间
        updateUserLastLoginTime(userAuthNew);
    }

    private void addLoginCookie(String token) {
        HttpServletResponse response = HttpContext.getResponse();
        if (response != null) {
            Cookie cookie = new Cookie(AuthConstants.TOKEN_NAME, token);
            cookie.setMaxAge(Math.toIntExact(JwtTokenUtils.getExpireSeconds()));
            cookie.setPath(StrUtil.SLASH);
            cookie.setHttpOnly(Boolean.TRUE);
            response.addCookie(cookie);
            System.out.println(1);
        }
    }

    public UserAuth getUserAuth(String userId) {
        return userAuthMapper.selectOne(Wrappers.lambdaQuery(UserAuth.class)
                                                .eq(UserAuth::getUserId, userId));
    }

    private void setContext(LoginUser loginUser) {
        ContextUtils.setContext(Context.builder()
                                       .currentUser(loginUser)
                                       .build());
    }

    public void logout() {
        userAuthMapper.delete(Wrappers.lambdaQuery(UserAuth.class)
                                      .eq(UserAuth::getUserId, ContextUtils.getCurrentUserId()));
    }

    public SysTokenLoginDTO sysTokenLogin() {
        LoginUser loginUser = ContextUtils.getCurrentUser();
        return JavaBeanUtils.map(loginUser, SysTokenLoginDTO.class);
    }

}
