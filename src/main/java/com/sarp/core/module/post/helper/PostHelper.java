package com.sarp.core.module.post.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.auth.service.MemberService;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.post.model.response.PlatformPostResponse;
import com.sarp.core.module.post.model.response.PostResponse;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/1/31 17:58
 */


@Component
@AllArgsConstructor
public class PostHelper {

    private CategoryMapper categoryMapper;
    private MemberService memberService;
    private MediaService mediaService;

    public void fillPostListData(List<? extends PostResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }

        Set<String> categoryIds = dataList.stream()
                                          .map(PostResponse::getCategoryId)
                                          .collect(Collectors.toSet());
        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);

        Map<String, Category> categoryMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(categoryList)) {
            categoryMap = categoryList.stream()
                                      .collect(Collectors.toMap(Category::getId, category -> category));
        }

        Set<String> createIds = dataList.stream()
                                        .map(PostResponse::getCreateId)
                                        .collect(Collectors.toSet());
        Map<String, Member> createUserMap = memberService.getMemberMap(createIds);

        List<String> postIds = dataList.stream()
                                       .map(PostResponse::getId)
                                       .collect(Collectors.toList());
        Map<String, List<Media>> mediaMap = mediaService.getMediaMap(postIds, UploadBizTypeEnum.POST);

        for (PostResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }
            Member createUser = createUserMap.get(response.getCreateId());
            if (ObjectUtil.isNotNull(createUser)) {
                response.setCreateUser(createUser.getNickName());
                response.setAvatar(createUser.getAvatar());
            }
            List<Media> mediaList = mediaMap.get(response.getId());
            if (CollUtil.isNotEmpty(mediaList)) {
                response.setPicUrl(mediaList.get(0).getPicUrl());
            }
        }
    }

    public void fillPlatformListAuditorData(List<PlatformPostResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> auditIds = dataList.stream()
                                       .map(PlatformPostResponse::getAuditId)
                                       .collect(Collectors.toSet());
        Map<String, Member> auditorMap = memberService.getMemberMap(auditIds);

        for (PlatformPostResponse response : dataList) {
            Member auditor = auditorMap.get(response.getAuditId());
            if (ObjectUtil.isNotNull(auditor)) {
                response.setAuditorName(auditor.getNickName());
            }
        }
    }

}
