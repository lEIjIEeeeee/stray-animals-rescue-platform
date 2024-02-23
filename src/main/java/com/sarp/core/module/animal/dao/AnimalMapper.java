package com.sarp.core.module.animal.dao;

import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @date 2024/1/30 17:55
 */

@Mapper
public interface AnimalMapper extends MyBaseMapper<Animal> {
}