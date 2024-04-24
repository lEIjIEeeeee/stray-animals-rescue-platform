package com.sarp.core.module.comment.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.auth.service.MemberService;
import com.sarp.core.module.comment.dao.CommentMapper;
import com.sarp.core.module.comment.enums.CommentSortTypeEnum;
import com.sarp.core.module.comment.enums.CommentTypeEnum;
import com.sarp.core.module.comment.enums.PlatformCommentSearchTypeEnum;
import com.sarp.core.module.comment.manager.CommentManager;
import com.sarp.core.module.comment.model.dto.CommentCountDTO;
import com.sarp.core.module.comment.model.dto.ReplayCommentDTO;
import com.sarp.core.module.comment.model.dto.ReplayCountDTO;
import com.sarp.core.module.comment.model.entity.Comment;
import com.sarp.core.module.comment.model.request.*;
import com.sarp.core.module.comment.model.response.CommentResponse;
import com.sarp.core.module.common.constant.NumberConstants;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.enums.PostStatusEnum;
import com.sarp.core.module.post.manager.PostManager;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/2/23 16:32
 */

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {

    private CommentMapper commentMapper;
    private PostMapper postMapper;

    private PostManager postManager;
    private CommentManager commentManager;
    private MemberManager memberManager;

    private MemberService memberService;

    public PageVO<CommentResponse> listPage(CommentQueryRequest request) {
        Post post = postManager.getByIdWithExp(request.getPostId());
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                                                           .eq(Comment::getPostId, post.getId())
                                                           .eq(Comment::getType, CommentTypeEnum.ROOT.name());
        if (CommentSortTypeEnum.LATEST.equals(request.getSortType())) {
            queryWrapper.orderByDesc(Comment::getCreateTime);
        }
        if (CommentSortTypeEnum.AUTHOR.equals(request.getSortType())) {
            List<Comment> authorReplayList = commentMapper.selectList(Wrappers.lambdaQuery(Comment.class)
                                                                              .eq(Comment::getPostId, post.getId())
                                                                              .eq(Comment::getType, CommentTypeEnum.REPLAY.name())
                                                                              .eq(Comment::getCreateId, post.getCreateId()));
            Set<String> authorReplayRootIds = authorReplayList.stream()
                                                              .map(Comment::getRootId)
                                                              .collect(Collectors.toSet());
            queryWrapper.and(wrapper -> wrapper.or(wp -> wp.eq(Comment::getCreateId, post.getCreateId()))
                                               .or(CollUtil.isNotEmpty(authorReplayRootIds), wp -> wp.in(Comment::getId, authorReplayRootIds)))
                        .orderByDesc(Comment::getCreateTime);
        }

        Page<Comment> commentPage = commentMapper.selectPage(PageUtils.createPage(request), queryWrapper);
        return convertCommentPageVo(request, post, commentPage);
    }

    private PageVO<CommentResponse> convertCommentPageVo(CommentQueryRequest request, Post post, Page<Comment> commentPage) {
        PageVO<CommentResponse> commentPageVO = CommonConvert.convertPageToPageVo(commentPage, CommentResponse.class);
        List<CommentResponse> dataList = commentPageVO.getDataList();

        if (CollUtil.isNotEmpty(dataList)) {
            fillCommentListData(dataList);

            List<String> rootIds = dataList.stream()
                                           .map(CommentResponse::getId)
                                           .collect(Collectors.toList());

            List<ReplayCountDTO> replayCountList = commentMapper.getReplayCountList(post.getId(), rootIds);
            List<ReplayCommentDTO> replayList = commentMapper.getReplayList(request.getReplayPageSize(), post.getId(), rootIds);

            if (CollUtil.isNotEmpty(replayList)) {
                fillReplayListData(replayList);
            }
            Map<String, ReplayCountDTO> replayCountMap = replayCountList.stream()
                                                                        .collect(Collectors.toMap(ReplayCountDTO::getRootId, commentCountDTO -> commentCountDTO));
            Map<String, List<ReplayCommentDTO>> replayDtoMap = replayList.stream()
                                                                         .collect(Collectors.groupingBy(ReplayCommentDTO::getRootId));
            for (CommentResponse response : dataList) {
                ReplayCountDTO replayCountDTO = replayCountMap.get(response.getId());
                List<ReplayCommentDTO> replayDtoList = replayDtoMap.get(response.getId());
                if (CollUtil.isEmpty(replayDtoList)) {
                    replayDtoList = Collections.emptyList();
                }
                PageVO<ReplayCommentDTO> replayCommentList = CommonConvert.convertListToPageVO(
                        BaseQueryRequest.builder()
                                        .pageNo(1L)
                                        .pageSize(request.getReplayPageSize())
                                        .build(),
                        ObjectUtil.isNotNull(replayCountDTO)
                                ? replayCountDTO.getReplayCount()
                                : NumberConstants.ZERO,
                        replayDtoList, ReplayCommentDTO.class);
                response.setReplayCommentList(replayCommentList);
            }
        }
        return commentPageVO;
    }

    private void fillCommentListData(List<CommentResponse> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> createIds = dataList.stream()
                                        .map(CommentResponse::getCreateId)
                                        .collect(Collectors.toSet());
        Map<String, Member> creatorMap = memberService.getMemberMap(createIds);
        for (CommentResponse response : dataList) {
            Member creator = creatorMap.get(response.getCreateId());
            if (ObjectUtil.isNotNull(creator)) {
                response.setNickName(creator.getNickName());
                response.setAvatar(creator.getAvatar());
            }
        }
    }

    public void fillReplayListData(List<ReplayCommentDTO> replayList) {
        if (CollUtil.isEmpty(replayList)) {
            return;
        }
        Set<String> createIds = replayList.stream()
                                          .map(ReplayCommentDTO::getCreateId)
                                          .collect(Collectors.toSet());
        Map<String, Member> creatorMap = memberService.getMemberMap(createIds);

        Set<String> toUserIds = replayList.stream()
                                          .map(ReplayCommentDTO::getToUserId)
                                          .collect(Collectors.toSet());
        Map<String, Member> toUserMap = memberService.getMemberMap(toUserIds);

        for (ReplayCommentDTO replay : replayList) {
            Member creator = creatorMap.get(replay.getCreateId());
            if (ObjectUtil.isNotNull(creator)) {
                replay.setNickName(creator.getNickName());
                replay.setAvatar(creator.getAvatar());
            }
            Member toUser = toUserMap.get(replay.getToUserId());
            if (ObjectUtil.isNotNull(toUser)) {
                replay.setToUserName(toUser.getNickName());
            }
        }
    }

    public Page<Comment> listPageReplay(ReplayCommentQueryRequest request) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                                                           .eq(Comment::getRootId, request.getRootId())
                                                           .orderByAsc(Comment::getCreateTime);
        return commentMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void sendComment(SendCommentRequest request) {
        Post post = postManager.getByIdWithExp(request.getId());
        if (PostStatusEnum.CLOSED.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "帖子已关闭");
        }
        Comment comment = Comment.builder()
                                 .type(CommentTypeEnum.ROOT.name())
                                 .postId(post.getId())
                                 .content(request.getContent())
                                 .build();
        commentMapper.insert(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    public ReplayCommentDTO replay(ReplayRequest request) {
        Comment comment = commentManager.getByIdWithExp(request.getId());
        Post post = postManager.getByIdWithExp(comment.getPostId());
        if (PostStatusEnum.CLOSED.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "帖子已关闭");
        }

        Comment replayComment = Comment.builder()
                                       .type(CommentTypeEnum.REPLAY.name())
                                       .pid(request.getId())
                                       .postId(post.getId())
                                       .toUserId(comment.getCreateId())
                                       .content(request.getContent())
                                       .build();
        if (CommentTypeEnum.ROOT.name().equals(comment.getType())) {
            replayComment.setRootId(comment.getId());
        } else if (CommentTypeEnum.REPLAY.name().equals(comment.getType())) {
            if (StrUtil.isBlank(comment.getRootId())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION);
            }
            replayComment.setRootId(comment.getRootId());
        } else {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
        commentMapper.insert(replayComment);

        ReplayCommentDTO replayCommentDTO = JavaBeanUtils.map(replayComment, ReplayCommentDTO.class);
        LoginUser currentUser = ContextUtils.getCurrentUser();
        replayCommentDTO.setNickName(currentUser.getNickName());
        replayCommentDTO.setAvatar(currentUser.getAvatar());
        Member toUser = memberManager.getByIdWithExp(replayCommentDTO.getToUserId());
        if (ObjectUtil.isNotNull(toUser)) {
            replayCommentDTO.setToUserName(toUser.getNickName());
        }
        return replayCommentDTO;
    }

    public CommentCountDTO getCounts(String postId) {
        List<Comment> commentList = commentMapper.selectList(Wrappers.lambdaQuery(Comment.class)
                                                                     .eq(Comment::getPostId, postId));
        return CommentCountDTO.builder()
                              .count((long) commentList.size())
                              .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(CommentDeleteRequest request) {
        Comment comment = commentManager.getByIdWithExp(request.getId());
        Post post = postManager.getByIdWithExp(comment.getPostId());
        if (PostStatusEnum.CLOSED.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "帖子已关闭");
        }
        commentMapper.deleteByIdWithFill(comment);
    }

    public Page<Comment> platformListPage(PlatformCommentQueryRequest request) {
        LambdaQueryWrapper<Comment> queryWrapper = Wrappers.lambdaQuery(Comment.class)
                                                           .ge(request.getStartTime() != null, Comment::getCreateTime, request.getStartTime())
                                                           .le(request.getEndTime() != null, Comment::getCreateTime, request.getEndTime())
                                                           .orderByDesc(Comment::getCreateTime);
        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (PlatformCommentSearchTypeEnum.COMMENT_USER.equals(request.getSearchType())) {
                List<Member> memberList = memberManager.getMemberListByNickName(request.getSearchContent());
                if (CollUtil.isEmpty(memberList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> userIds = memberList.stream()
                                                 .map(Member::getId)
                                                 .collect(Collectors.toList());
                queryWrapper.in(Comment::getCreateId, userIds);
            }
            if (PlatformCommentSearchTypeEnum.POST_TITLE.equals(request.getSearchType())) {
                List<Post> postList = postMapper.selectList(Wrappers.lambdaQuery(Post.class)
                                                                    .like(Post::getTitle, request.getSearchContent()));
                if (CollUtil.isEmpty(postList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> postIds = postList.stream()
                                               .map(Post::getId)
                                               .collect(Collectors.toList());
                queryWrapper.in(Comment::getPostId, postIds);
            }
        }

        if (request.getType() != null) {
            queryWrapper.eq(Comment::getType, request.getType());
        }
        return commentMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void platformDelete(CommentDeleteRequest request) {
        Comment comment = commentManager.getByIdWithExp(request.getId());
        commentMapper.deleteByIdWithFill(comment);
    }

}
