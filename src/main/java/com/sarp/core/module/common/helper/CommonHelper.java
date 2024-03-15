package com.sarp.core.module.common.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.model.response.PersonalAnimalResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/3/12 10:49
 */

@Component
@AllArgsConstructor
public class CommonHelper {

    private CategoryMapper categoryMapper;


    public void fillPersonalAnimalListData(List<PersonalAnimalResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> categoryIds = dataList.stream()
                                          .map(PersonalAnimalResponse::getCategoryId)
                                          .collect(Collectors.toSet());
        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);
        if (CollUtil.isEmpty(categoryList)) {
            return;
        }
        Map<String, Category> categoryMap = categoryList.stream()
                                                        .collect(Collectors.toMap(Category::getId, category -> category));

        for (PersonalAnimalResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }
        }
    }

}
