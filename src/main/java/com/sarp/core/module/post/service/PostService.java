package com.sarp.core.module.post.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.category.manager.CategoryManager;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.enums.AuditResultEnum;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.media.dao.MediaMapper;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.enums.PostSearchTypeEnum;
import com.sarp.core.module.post.enums.PostStatusEnum;
import com.sarp.core.module.post.manager.PostManager;
import com.sarp.core.module.post.model.dto.PostCloseReasonDTO;
import com.sarp.core.module.post.model.dto.PostDetailDTO;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.post.model.request.*;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @date 2024/1/30 15:49
 */

@Service
@Slf4j
@AllArgsConstructor
public class PostService {

    private PostMapper postMapper;
    private MemberMapper memberMapper;
    private MediaMapper mediaMapper;

    private CategoryService categoryService;
    private MediaService mediaService;

    private PostManager postManager;
    private MemberManager memberManager;
    private CategoryManager categoryManager;

    @Transactional(rollbackFor = Exception.class)
    public void submitPost(SubmitPostRequest request) {
        Post post = JavaBeanUtils.map(request, Post.class, BaseDO.ID);
        post.setBizType(request.getBizType().getCode())
            .setStatus(PostStatusEnum.AUDIT_WAIT.getCode());
        postMapper.insert(post);

        Media media = Media.builder()
                           .serviceId(post.getId())
                           .serviceType(UploadBizTypeEnum.POST.name())
                           .picUrl(request.getPicUrl())
                           .sort(1)
                           .build();
        mediaMapper.insert(media);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resubmitPost(SubmitPostRequest request) {
        String userId = ContextUtils.getCurrentUserId();

        Post post = postManager.getByIdWithExp(request.getId());
        checkPostInfo(post, userId);

        JavaBeanUtils.map(request, post);
        post.setBizType(request.getBizType().getCode())
            .setStatus(PostStatusEnum.AUDIT_WAIT.getCode());
        postMapper.updateById(post);
    }

    private void checkPostInfo(Post post, String userId) {
        if (ObjectUtil.notEqual(PostStatusEnum.AUDIT_REJECT.getCode(), post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前帖子状态异常，无法进行操作");
        }
        if (ObjectUtil.notEqual(post.getCreateId(), userId)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前帖子没有操作权限");
        }
    }

    public PostDetailDTO get(String id) {
        Post post = postManager.getByIdWithExp(id);
        PostDetailDTO postDetail = JavaBeanUtils.map(post, PostDetailDTO.class);

        Member createUser = memberManager.getByIdWithExp(post.getCreateId());
        postDetail.setNickName(createUser.getNickName());
        postDetail.setAvatar(createUser.getAvatar());

        Category category = categoryManager.getByIdWithExp(post.getCategoryId());
        postDetail.setCategoryName(category.getName());

        List<Media> postMediaList = mediaService.getMediaList(post.getId(), UploadBizTypeEnum.POST);
        if (CollUtil.isNotEmpty(postMediaList)) {
            postDetail.setPicUrl(postMediaList.get(0).getPicUrl());
        }
        return postDetail;
    }

    public Page<Post> listPage(PostQueryRequest request) {
        LambdaQueryWrapper<Post> queryWrapper = Wrappers.lambdaQuery(Post.class)
                                                        .eq(StrUtil.isNotBlank(request.getUserId()), Post::getCreateId, request.getUserId())
                                                        .ne(Post::getStatus, PostStatusEnum.AUDIT_WAIT.getCode())
                                                        .isNotNull(Post::getStatus);

        boolean queryFlag = buildPostListQueryWrapper(request, queryWrapper);
        if (!queryFlag) {
            return PageUtils.createEmptyPage();
        }
        return postMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public Page<Post> listPagePlatform(PlatformPostQueryRequest request) {
        LambdaQueryWrapper<Post> queryWrapper = Wrappers.lambdaQuery(Post.class)
                                                        .ge(request.getAuditStartDate() != null, Post::getAuditTime, request.getAuditStartDate())
                                                        .le(request.getAuditEndDate() != null, Post::getAuditTime, request.getAuditEndDate());
        if (request.getStatus() != null) {
            queryWrapper.eq(Post::getStatus, request.getStatus().getCode());
        }

        boolean queryFlag = buildPostListQueryWrapper(request, queryWrapper);
        if (!queryFlag) {
            return PageUtils.createEmptyPage();
        }
        return postMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    private boolean buildPostListQueryWrapper(PostQueryRequest request, LambdaQueryWrapper<Post> queryWrapper) {
        queryWrapper.ge(request.getCreateStartDate() != null, Post::getCreateTime, request.getCreateStartDate())
                    .le(request.getCreateEndDate() != null, Post::getCreateTime, request.getCreateEndDate())
                    .orderByDesc(Post::getUpdateTime)
                    .orderByDesc(Post::getCreateTime);

        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (PostSearchTypeEnum.POST_TITLE == request.getSearchType()) {
                queryWrapper.like(Post::getTitle, request.getSearchContent());
            }
            if (PostSearchTypeEnum.CREATE_USER.equals(request.getSearchType())) {
                List<Member> memberList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class)
                                                                          .like(Member::getNickName, request.getSearchContent()));
                if (CollUtil.isEmpty(memberList)) {
                    return false;
                }
                List<String> userIds = memberList.stream()
                                                 .map(Member::getId)
                                                 .collect(Collectors.toList());
                queryWrapper.in(Post::getCreateId, userIds);
            }
        }

        if (request.getBizType() != null) {
            queryWrapper.eq(Post::getBizType, request.getBizType().getCode());
        }

        if (CollUtil.isNotEmpty(request.getCategoryIds())) {
            Set<String> recurveDownCategoryIds = categoryService.getRecursiveCategoryIds(request.getCategoryIds());
            if (CollUtil.isEmpty(recurveDownCategoryIds)) {
                return false;
            }
            queryWrapper.in(Post::getCategoryId, recurveDownCategoryIds);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(PostAuditRequest request) {
        Post post = postManager.getByIdWithExp(request.getId());
        if (!PostStatusEnum.AUDIT_WAIT.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "该帖子状态异常，无法进行审核操作");
        }

        if (AuditResultEnum.PASS.equals(request.getAuditResult())) {
            post.setStatus(PostStatusEnum.AUDIT_PASS.getCode());
        } else {
            if (StrUtil.isBlank(request.getAuditRemark())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "审核拒绝时备注不能为空");
            }
            post.setStatus(PostStatusEnum.AUDIT_REJECT.getCode());
        }

        post.setAuditId(ContextUtils.getCurrentUserId())
            .setAuditTime(DateUtil.date())
            .setAuditRemark(request.getAuditRemark());
        postMapper.updateById(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public void close(PostCloseRequest request) {
        Post post = postManager.getByIdWithExp(request.getId());
        if (PostStatusEnum.CLOSED.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该帖子已被关闭，无需操作");
        }
        post.setStatus(PostStatusEnum.CLOSED.getCode())
            .setCloseReason(request.getCloseReason());
        postMapper.updateById(post);
    }

    public PostCloseReasonDTO getCloseReason(String id) {
        Post post = postManager.getByIdWithExp(id);
        return PostCloseReasonDTO.builder()
                                 .closeReason(post.getCloseReason())
                                 .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(PostDeleteRequest request) {
        Post post = postManager.getByIdWithExp(request.getId());
        postMapper.deleteByIdWithFill(post);
    }

}
