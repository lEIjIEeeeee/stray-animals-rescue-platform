package com.sarp.core.module.auth.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.enums.UserSearchTypeEnum;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.auth.manager.UserManager;
import com.sarp.core.module.auth.model.dto.UserDetailDTO;
import com.sarp.core.module.auth.model.request.ChangeStatusRequest;
import com.sarp.core.module.auth.model.request.UserIdRequest;
import com.sarp.core.module.auth.model.request.UserQueryRequest;
import com.sarp.core.module.auth.model.request.UserRequest;
import com.sarp.core.module.auth.util.AuthNoGenerateUtils;
import com.sarp.core.module.common.constant.CommonConstants;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.module.user.model.entity.User;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2024/3/18 11:13
 */

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;
    private MemberMapper memberMapper;

    private UserManager userManager;
    private MemberManager memberManager;

    private LoginService loginService;

    public Page<User> listPage(UserQueryRequest request) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery(User.class)
                                                        .ge(request.getCreateStartDate() != null, User::getCreateTime, request.getCreateStartDate())
                                                        .le(request.getCreateEndDate() != null, User::getCreateTime, request.getCreateEndDate())
                                                        .orderByDesc(User::getUpdateTime);
        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (UserSearchTypeEnum.LOGIN_ACCOUNT.equals(request.getSearchType())) {
                queryWrapper.like(User::getAccount, request.getSearchContent());
            }
            if (UserSearchTypeEnum.PHONE.equals(request.getSearchType())) {
                queryWrapper.like(User::getPhone, request.getSearchContent());
            }
            if (UserSearchTypeEnum.NICK_NAME.equals(request.getSearchType())) {
                queryWrapper.like(User::getName, request.getSearchContent());
            }
        }
        if (request.getUserType() != null) {
            queryWrapper.eq(User::getUserType, request.getUserType().name());
        }
        if (request.getGender() != null) {
            queryWrapper.eq(User::getGender, request.getGender().name());
        }
        if (request.getStatus() != null) {
            queryWrapper.eq(User::getStatus, request.getStatus().getCode());
        }
        return userMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public UserDetailDTO get(String id) {
        User user = userManager.getByIdWithExp(id);
        UserDetailDTO userDetail = JavaBeanUtils.map(user, UserDetailDTO.class);
        Member member = memberManager.getByIdWithExp(id);
        userDetail.setNickName(member.getNickName());
        userDetail.setRealName(member.getRealName());
        return userDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(UserRequest request) {
        String userId = ContextUtils.getCurrentUserId();

        if (ObjectUtil.notEqual(request.getPassword(), request.getConfirmPassword())) {
            throw new BizException(HttpResultCode.PARAM_VALIDATED_FAILED, "两次输入的密码不一致");
        }
        checkUserInfo(request);

        String account = loginService.generateAccount();
        String salt = AuthNoGenerateUtils.generateSalt();
        String encryptPwd = LoginService.encryptPwd(request.getPassword(), salt);

        User user = JavaBeanUtils.map(request, User.class, BaseDO.ID);
        user.setAccount(account)
            .setName(request.getNickName())
            .setPassword(encryptPwd)
            .setSalt(salt)
            .setStatus(request.getStatus().getCode())
            .setCreateId(userId)
            .setCreateTime(DateUtil.date())
            .setUpdateId(userId)
            .setUpdateTime(DateUtil.date());
        userMapper.insert(user);

        Member member = JavaBeanUtils.map(user, Member.class);
        member.setNickName(request.getNickName())
              .setRealName(request.getRealName());
        memberMapper.insert(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(UserRequest request) {
        String userId = ContextUtils.getCurrentUserId();
        checkUserInfo(request);

        User user = userManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, user);
        user.setName(request.getNickName())
            .setStatus(request.getStatus().getCode())
            .setUpdateId(userId)
            .setUpdateTime(DateUtil.date());
        userMapper.updateById(user);

        Member member = memberManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, member);
        member.setStatus(request.getStatus().getCode());
        memberMapper.updateById(member);
    }

    private void checkUserInfo(UserRequest request) {
        User userByPhone = userManager.getUserByPhone(request.getPhone());
        if (ObjectUtil.isNull(userByPhone)) {
            return;
        }
        if (StrUtil.isBlank(request.getId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "手机号已被注册");
        }
        if (StrUtil.isNotBlank(request.getId())
                && ObjectUtil.notEqual(request.getId(), userByPhone.getId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "手机号已被注册");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(ChangeStatusRequest request) {
        User user = userManager.getByIdWithExp(request.getId());
        user.setStatus(request.getStatus().getCode())
            .setUpdateId(ContextUtils.getCurrentUserId())
            .setUpdateTime(DateUtil.date());
        userMapper.updateById(user);

        Member member = memberManager.getByIdWithExp(request.getId());
        member.setStatus(request.getStatus().getCode());
        memberMapper.updateById(member);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(UserIdRequest request) {
        User user = userManager.getByIdWithExp(request.getId());
        String encryptPwd = LoginService.encryptPwd(CommonConstants.DEFAULT_USER_PASSWORD, user.getSalt());
        user.setPassword(encryptPwd)
            .setUpdateId(ContextUtils.getCurrentUserId())
            .setUpdateTime(DateUtil.date());
        userMapper.updateById(user);
    }

}
