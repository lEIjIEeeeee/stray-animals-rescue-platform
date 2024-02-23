package com.sarp.core.module.post.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.constant.NumberConstants;
import com.sarp.core.module.common.constant.SymbolConstants;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.post.model.entity.Post;
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

    public void fillPostAbstractData(Page<Post> postPage, PageVO<? extends PostResponse> postPageVO) {
        List<Post> postList = postPage.getRecords();
        if (CollUtil.isEmpty(postList)) {
            return;
        }
        Map<String, Post> postMap = postList.stream()
                                            .collect(Collectors.toMap(Post::getId, post -> post));

        List<? extends PostResponse> dataList = postPageVO.getDataList();
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        for (PostResponse response : dataList) {
            Post post = postMap.get(response.getId());
            if (ObjectUtil.isNotNull(post)) {
                String postAbstract = post.getContent();
                if (StrUtil.length(post.getContent()) > 100) {
                    postAbstract = StrUtil.sub(post.getContent(),
                            NumberConstants.ZERO, NumberConstants.ONE_HUNDRED) + SymbolConstants.ELLIPSIS;
                }
                response.setPostAbstract(postAbstract);
            }
        }
    }

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
