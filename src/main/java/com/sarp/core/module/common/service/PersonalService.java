package com.sarp.core.module.common.service;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.auth.manager.UserManager;
import com.sarp.core.module.auth.service.LoginService;
import com.sarp.core.module.auth.util.AuthNoGenerateUtils;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.common.model.request.PersonalInfoEditRequest;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.module.user.model.entity.User;
import com.sarp.core.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @date 2024/4/1 16:58
 */

@Service
@Slf4j
@AllArgsConstructor
public class PersonalService {

    private UserMapper userMapper;
    private MemberMapper memberMapper;

    private UserManager userManager;
    private MemberManager memberManager;

    @Transactional(rollbackFor = Exception.class)
    public void editPersonalInfo(PersonalInfoEditRequest request) {
        if (ObjectUtil.notEqual(request.getId(), ContextUtils.getCurrentUserId())) {
            throw new BizException(HttpResultCode.USER_STATUS_ERROR, "用户信息异常");
        }

        User userByPhone = userManager.getUserByPhone(request.getPhone());
        if (ObjectUtil.isNotNull(userByPhone)
                && ObjectUtil.notEqual(request.getId(), userByPhone.getId())) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "该手机号已被注册");
        }

        User user = userManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, user, BaseDO.ID);
        user.setName(request.getNickName())
            .setGender(request.getGender().name());
        userMapper.updateById(user);

        Member member = memberManager.getByIdWithExp(request.getId());
        JavaBeanUtils.map(request, member, BaseDO.ID);
        member.setGender(request.getGender().name());
        memberMapper.updateById(member);
    }

}
