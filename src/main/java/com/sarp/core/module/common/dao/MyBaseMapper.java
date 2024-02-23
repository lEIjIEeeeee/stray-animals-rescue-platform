package com.sarp.core.module.common.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @date 2024/1/22 16:09
 */

public interface MyBaseMapper<T> extends BaseMapper<T> {

    int deleteByIdWithFill(T param);

    int deleteBatchWithFill(@Param(Constants.ENTITY) T param, @Param(Constants.WRAPPER) Wrapper<T> wrapper);

    Integer insertBatchSomeColumn(Collection<T> entityList);

}
