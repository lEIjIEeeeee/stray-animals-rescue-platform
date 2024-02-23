package com.sarp.core.module.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sarp.core.module.auth.model.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/27 14:13
 *
*/

@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {
}