package com.sarp.core.module.category.dao;

import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @date 2024/1/31 16:45
 *
*/

@Mapper
public interface CategoryMapper extends MyBaseMapper<Category> {

    String recurveUpCategoryId(@Param("categoryId") String categoryId);

    String recurveDownCategoryId(@Param("categoryId") String categoryId);

    Set<String> recursiveUpCategoryId(@Param("id") String id);

    Set<String> recursiveDownCategoryId(@Param("id") String id);
}