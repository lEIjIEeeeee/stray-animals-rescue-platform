package com.sarp.core.module.user.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.module.user.dao.UserMapper;
import com.sarp.core.module.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2024/1/26 16:00
 */

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserMapper userMapper;

    public User getUserByAccount(String account) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getAccount, account));
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getPhone, phone));
    }

}
