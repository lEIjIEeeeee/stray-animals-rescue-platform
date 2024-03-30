package com.sarp.core.module.category.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/29 0:11
 */

@Component
@AllArgsConstructor
public class CategoryManager {

    private CategoryMapper categoryMapper;

    public Category getById(String id) {
        return categoryMapper.selectById(id);
    }

    public Category getByIdWithExp(String id) {
        Category category = categoryMapper.selectById(id);
        if (ObjectUtil.isNull(category)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return category;
    }

}
