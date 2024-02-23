package com.sarp.core.module.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sarp.core.module.user.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/26 15:54
 *
*/

@Mapper
public interface UserMapper extends BaseMapper<User> {
}