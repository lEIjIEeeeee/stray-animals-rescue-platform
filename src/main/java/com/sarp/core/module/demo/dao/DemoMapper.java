package com.sarp.core.module.demo.dao;

import com.sarp.core.module.common.dao.MyBaseMapper;
import com.sarp.core.module.demo.model.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/22 14:57
 */

@Mapper
public interface DemoMapper extends MyBaseMapper<Demo> {
}