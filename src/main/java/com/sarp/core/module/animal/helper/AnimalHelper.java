package com.sarp.core.module.animal.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.module.animal.model.dto.AnimalAdoptRecordDTO;
import com.sarp.core.module.animal.model.dto.AnimalBaseInfoDTO;
import com.sarp.core.module.animal.model.response.AnimalResponse;
import com.sarp.core.module.animal.model.response.PlatformAnimalResponse;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.manager.CategoryManager;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.media.dao.MediaMapper;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/3/7 0:43
 */

@Component
@AllArgsConstructor
public class AnimalHelper {

    private CategoryMapper categoryMapper;
    private MemberMapper memberMapper;
    private MediaMapper mediaMapper;

    private CategoryManager categoryManager;
    private MemberManager memberManager;

    private MediaService mediaService;

    public void fillAnimalListData(List<AnimalResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> categoryIds = dataList.stream()
                                          .map(AnimalResponse::getCategoryId)
                                          .collect(Collectors.toSet());

        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);

        Map<String, Category> categoryMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(categoryList)) {
            categoryMap = categoryList.stream()
                                      .collect(Collectors.toMap(Category::getId, category -> category));
        }

        for (AnimalResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }
        }
    }

    public void fillAnimalListMediaData(List<AnimalResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        List<String> animalIds = dataList.stream()
                                         .map(AnimalResponse::getId)
                                         .collect(Collectors.toList());
        List<Media> animalMediaList = mediaMapper.selectList(Wrappers.lambdaQuery(Media.class)
                                                                     .in(Media::getServiceId, animalIds)
                                                                     .eq(Media::getServiceType, UploadBizTypeEnum.ANIMAL.name()));
        if (CollUtil.isEmpty(animalMediaList)) {
            return;
        }
        Map<String, List<Media>> mediaMapByAnimal = animalMediaList.stream()
                                                                   .collect(Collectors.groupingBy(Media::getServiceId));
        for (AnimalResponse response : dataList) {
            List<Media> mediaList = mediaMapByAnimal.get(response.getId());
            if (CollUtil.isNotEmpty(mediaList)) {
                response.setPicUrl(mediaList.get(0).getPicUrl());
            }
        }
    }

    public void fillPlatformAnimalListData(List<PlatformAnimalResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }

        Set<String> categoryIds = dataList.stream()
                                          .map(PlatformAnimalResponse::getCategoryId)
                                          .collect(Collectors.toSet());

        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);

        Map<String, Category> categoryMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(categoryList)) {
            categoryMap = categoryList.stream()
                                      .collect(Collectors.toMap(Category::getId, category -> category));
        }

        Set<String> ownerIds = dataList.stream()
                                       .map(PlatformAnimalResponse::getOwnerId)
                                       .collect(Collectors.toSet());
        List<Member> ownerList = memberMapper.selectBatchIds(ownerIds);

        Map<String, Member> ownerMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(ownerList)) {
            ownerMap = ownerList.stream()
                                .collect(Collectors.toMap(Member::getId, member -> member));
        }

        for (PlatformAnimalResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }
            Member owner = ownerMap.get(response.getOwnerId());
            if (ObjectUtil.isNotNull(owner)) {
                response.setOwnerName(owner.getNickName());
            }
        }
    }

    public void fillAnimalBaseInfo(AnimalBaseInfoDTO baseInfo) {
        Category category = categoryManager.getById(baseInfo.getCategoryId());
        if (ObjectUtil.isNotNull(category)) {
            baseInfo.setCategoryName(category.getName());
        }

        Member owner = memberManager.getById(baseInfo.getOwnerId());
        if (ObjectUtil.isNotNull(owner)) {
            baseInfo.setOwnerName(owner.getNickName());
            baseInfo.setOwnerPhone(owner.getPhone());
        }

        List<Media> animalMediaList = mediaService.getMediaList(baseInfo.getId(), UploadBizTypeEnum.ANIMAL);
        if (CollUtil.isNotEmpty(animalMediaList)) {
            baseInfo.setPicUrl(animalMediaList.get(0).getPicUrl());
        }
    }

    public void fillAnimalDetailAdoptRecordData(List<AnimalAdoptRecordDTO> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
    }

}
