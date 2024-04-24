package com.sarp.core.module.comment.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.module.auth.service.MemberService;
import com.sarp.core.module.comment.model.response.PlatformCommentResponse;
import com.sarp.core.module.post.manager.PostManager;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.user.model.entity.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/4/19 13:54
 */

@Component
@AllArgsConstructor
public class CommentHelper {

    private PostManager postManager;
    private MemberService memberService;

    public void fillPlatformCommentListData(List<PlatformCommentResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> postIds = dataList.stream()
                                      .map(PlatformCommentResponse::getPostId)
                                      .collect(Collectors.toSet());
        Map<String, Post> postMap = postManager.getPostMap(postIds);

        Set<String> createIds = dataList.stream()
                                        .map(PlatformCommentResponse::getCreateId)
                                        .collect(Collectors.toSet());
        Map<String, Member> creatorMap = memberService.getMemberMap(createIds);

        Set<String> toUserIds = dataList.stream()
                                        .map(PlatformCommentResponse::getToUserId)
                                        .collect(Collectors.toSet());
        Map<String, Member> toUserMap = memberService.getMemberMap(toUserIds);
        for (PlatformCommentResponse response : dataList) {
            Post post = postMap.get(response.getPostId());
            if (ObjectUtil.isNotNull(post)) {
                response.setTitle(post.getTitle());
            }
            Member creator = creatorMap.get(response.getCreateId());
            if (ObjectUtil.isNotNull(creator)) {
                response.setCreateName(creator.getNickName());
            }
            Member toUser = toUserMap.get(response.getToUserId());
            if (ObjectUtil.isNotNull(toUser)) {
                response.setToUserName(toUser.getNickName());
            }
        }
    }

}
