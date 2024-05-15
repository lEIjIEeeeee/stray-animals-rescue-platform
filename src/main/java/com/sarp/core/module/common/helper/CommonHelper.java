package com.sarp.core.module.common.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.sarp.core.module.animal.manager.AnimalManager;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.auth.service.MemberService;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.common.model.response.AdoptRecordResponse;
import com.sarp.core.module.common.model.response.PersonalAnimalResponse;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.user.model.entity.Member;
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
    private MemberService memberService;

    private AnimalManager animalManager;

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

    public void fillAdoptRecordListData(List<AdoptRecordResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> animalIds = dataList.stream()
                                        .map(AdoptRecordResponse::getAnimalId)
                                        .collect(Collectors.toSet());
        Map<String, Animal> animalMap = animalManager.getAnimalMap(animalIds);

        Set<String> categoryIds = animalMap.values()
                                           .stream()
                                           .map(Animal::getCategoryId)
                                           .collect(Collectors.toSet());
        Map<String, Category> categoryMap = categoryService.getCategoryMap(categoryIds);

        Map<String, List<Media>> mediaMap = mediaService.getMediaMap(animalIds, UploadBizTypeEnum.ANIMAL);

        Set<String> auditIds = dataList.stream()
                                       .map(AdoptRecordResponse::getAuditId)
                                       .collect(Collectors.toSet());
        Map<String, Member> auditorMap = memberService.getMemberMap(auditIds);

        for (AdoptRecordResponse response : dataList) {
            Animal animal = animalMap.get(response.getAnimalId());
            if (ObjectUtil.isNotNull(animal)) {
                response.setAnimalNo(animal.getAnimalNo());
                response.setAnimalName(animal.getName());
                Category category = categoryMap.get(animal.getCategoryId());
                if (ObjectUtil.isNotNull(category)) {
                    response.setCategoryName(category.getName());
                }
                List<Media> mediaList = mediaMap.get(animal.getId());
                if (CollUtil.isNotEmpty(mediaList)) {
                    response.setAnimalPic(mediaList.get(0).getPicUrl());
                }
            }
            if (StrUtil.isNotBlank(response.getAuditId())) {
                Member auditor = auditorMap.get(response.getAuditId());
                response.setAuditorName(auditor.getNickName());
            }
        }
    }

}
