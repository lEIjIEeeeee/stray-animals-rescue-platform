package com.sarp.core.module.category.dao;

import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @date 2024/1/31 16:45
 *
*/

@Mapper
public interface CategoryMapper extends MyBaseMapper<Category> {

    String recurveDownCategoryId(@Param("id") String id);

}