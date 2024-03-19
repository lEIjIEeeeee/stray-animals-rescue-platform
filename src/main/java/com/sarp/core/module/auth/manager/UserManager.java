package com.sarp.core.module.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/19 10:31
 */

@Component
@AllArgsConstructor
public class UserManager {

    private UserMapper userMapper;

    public User getByIdWithExp(String userId) {
        User user = userMapper.selectById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return user;
    }

    public User getUserByAccount(String account) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getAccount, account));
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getPhone, phone));
    }

}
