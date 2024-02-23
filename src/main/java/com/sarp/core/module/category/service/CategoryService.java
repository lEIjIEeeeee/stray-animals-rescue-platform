package com.sarp.core.module.category.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.sarp.core.module.category.dao.CategoryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * @date 2024/1/31 16:46
 */

@Service
@Slf4j
@AllArgsConstructor
public class CategoryService {

    private CategoryMapper categoryMapper;

    public Set<String> recurveDownCategoryId(String id) {
        if (StrUtil.isBlank(id)) {
            return Collections.emptySet();
        }

        String recurveDownCategoryIds = categoryMapper.recurveDownCategoryId(id);
        if (StrUtil.isBlank(recurveDownCategoryIds)) {
            return Collections.emptySet();
        }

        return CollUtil.newHashSet(StrUtil.split(recurveDownCategoryIds, StrUtil.COMMA));
    }

}
