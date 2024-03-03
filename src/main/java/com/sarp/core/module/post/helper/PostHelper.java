package com.sarp.core.module.post.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.post.model.response.PostResponse;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
    private MemberMapper memberMapper;

    public void fillPostListData(List<? extends PostResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }

        Set<String> categoryIds = dataList.stream()
                                          .map(PostResponse::getCategoryId)
                                          .collect(Collectors.toSet());
        List<Category> categoryList = categoryMapper.selectBatchIds(categoryIds);
        if (CollUtil.isEmpty(categoryList)) {
            return;
        }
        Map<String, Category> categoryMap = categoryList.stream()
                                                        .collect(Collectors.toMap(Category::getId, category -> category));

        Set<String> createIds = dataList.stream()
                                        .map(PostResponse::getCreateId)
                                        .collect(Collectors.toSet());
        List<Member> createUserList = memberMapper.selectBatchIds(createIds);
        if (CollUtil.isEmpty(createUserList)) {
            return;
        }
        Map<String, Member> createUserMap = createUserList.stream()
                                                          .collect(Collectors.toMap(Member::getId, member -> member));

        for (PostResponse response : dataList) {
            Category category = categoryMap.get(response.getCategoryId());
            if (ObjectUtil.isNotNull(category)) {
                response.setCategoryName(category.getName());
            }
            Member createUser = createUserMap.get(response.getCreateId());
            if (ObjectUtil.isNotNull(createUser)) {
                response.setCreateUser(createUser.getNickName());
            }
        }
    }

}
