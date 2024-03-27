package com.sarp.core.module.animal.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.model.dto.PlatformAnimalDetailDTO;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.model.response.AnimalResponse;
import com.sarp.core.module.animal.model.response.PlatformAnimalResponse;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.enums.HttpResultCode;
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

    public void fillAnimalDetailData(PlatformAnimalDetailDTO animalDetail) {
        Category category = categoryMapper.selectById(animalDetail.getCategoryId());
        if (ObjectUtil.isNull(category)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        animalDetail.setCategoryName(category.getName());

        Member owner = memberMapper.selectById(animalDetail.getOwnerId());
        if (ObjectUtil.isNotNull(owner)) {
            animalDetail.setOwnerName(owner.getNickName());
        }
    }

}
