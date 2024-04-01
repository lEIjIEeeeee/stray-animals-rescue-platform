package com.sarp.core.module.common.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.animal.manager.AnimalManager;
import com.sarp.core.module.animal.service.AnimalService;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.common.model.response.PersonalAnimalResponse;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
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

    private CategoryService categoryService;
    private MediaService mediaService;

    public void fillPersonalAnimalListData(List<PersonalAnimalResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> categoryIds = dataList.stream()
                                          .map(PersonalAnimalResponse::getCategoryId)
                                          .collect(Collectors.toSet());
        Map<String, Category> categoryMap = categoryService.getCategoryMap(categoryIds);


        List<String> animalIds = dataList.stream()
                                         .map(PersonalAnimalResponse::getId)
                                         .collect(Collectors.toList());
        Map<String, List<Media>> animalMediaMap = mediaService.getMediaMap(animalIds, UploadBizTypeEnum.ANIMAL);

        for (PersonalAnimalResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }

            List<Media> mediaList = animalMediaMap.get(response.getId());
            if (CollUtil.isNotEmpty(mediaList)) {
                response.setPicUrl(mediaList.get(0).getPicUrl());
            }
        }
    }

}
