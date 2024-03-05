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
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.service.AnimalService;
import com.sarp.core.module.animal.util.AnimalNoGenerateUtils;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.common.enums.AuditResultEnum;
import com.sarp.core.module.common.enums.BizTypeEnum;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.YesOrNoEnum;
import com.sarp.core.module.common.model.entity.BaseDO;
import com.sarp.core.module.post.dao.PostMapper;
import com.sarp.core.module.post.enums.PostSearchTypeEnum;
import com.sarp.core.module.post.enums.PostStatusEnum;
import com.sarp.core.module.post.model.dto.PostCloseReasonDTO;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.post.model.request.*;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.enums.UserTypeEnum;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
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
    private CategoryMapper categoryMapper;
    private AnimalMapper animalMapper;
    private MemberMapper memberMapper;

    private AnimalService animalService;

    @Transactional(rollbackFor = Exception.class)
    public void submitPost(SubmitPostRequest request) {
        LoginUser user = ContextUtils.getCurrentUser();
        if (UserTypeEnum.NORMAL_USER.name().equals(user.getUserType())) {
            checkAnimalOwner(request, user.getId());
        }
        if (StrUtil.isNotBlank(request.getAnimalId())) {
            checkAnimalStatus(request);
        }

        Post post = JavaBeanUtils.map(request, Post.class, BaseDO.ID);
        post.setBizType(request.getBizType().getCode())
            .setStatus(PostStatusEnum.AUDIT_WAIT.getCode());
        postMapper.insert(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public void resubmitPost(SubmitPostRequest request) {
        LoginUser user = ContextUtils.getCurrentUser();
        if (UserTypeEnum.NORMAL_USER.name().equals(user.getUserType())) {
            checkAnimalOwner(request, user.getId());
        }
        if (StrUtil.isNotBlank(request.getAnimalId())) {
            checkAnimalStatus(request);
        }

        Post post = getByIdWithExp(request.getId());
        checkPostInfo(post, user.getId());

        JavaBeanUtils.map(request, post);
        post.setBizType(request.getBizType().getCode())
            .setStatus(PostStatusEnum.AUDIT_WAIT.getCode());
        postMapper.updateById(post);
    }

    private void checkAnimalOwner(SubmitPostRequest request, String userId) {
        if (StrUtil.isNotBlank(request.getAnimalId())) {
            Animal animal = animalService.getByIdWithExp(request.getAnimalId());
            if (ObjectUtil.notEqual(animal.getOwnerId(), userId)) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前选择不是你名下的宠物，没有操作权限");
            }
        }
    }

    private void checkAnimalStatus(SubmitPostRequest request) {
        Animal animal = animalService.getByIdWithExp(request.getAnimalId());
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())
                && BizTypeEnum.ADOPT_BIZ.equals(request.getBizType())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，无法发起领养帖");
        }
    }

    private void checkPostInfo(Post post, String userId) {
        if (ObjectUtil.notEqual(PostStatusEnum.AUDIT_REJECT.getCode(), post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前帖子状态异常，无法进行操作");
        }
        if (ObjectUtil.notEqual(post.getCreateId(), userId)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前帖子没有操作权限");
        }
    }

    public Page<Post> listPage(PostQueryRequest request) {
        LambdaQueryWrapper<Post> queryWrapper = Wrappers.lambdaQuery(Post.class)
                                                        .ne(Post::getStatus, PostStatusEnum.AUDIT_WAIT.getCode())
                                                        //TODO 待审核排在最前面
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
            queryWrapper.eq(Post::getStatus, request.getStatus());
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
            if (PostSearchTypeEnum.ANIMAL_NAME == request.getSearchType()) {
                queryWrapper.like(Post::getAnimalName, request.getSearchContent());
            }
        }

        if (request.getBizType() != null) {
            queryWrapper.eq(Post::getBizType, request.getBizType().getCode());
        }

        if (CollUtil.isNotEmpty(request.getCategoryIds())) {
            Set<String> recurveDownCategoryIds = getRecurveDownCategoryIds(request.getCategoryIds());
            if (CollUtil.isEmpty(recurveDownCategoryIds)) {
                return false;
            }
            queryWrapper.in(Post::getCategoryId, recurveDownCategoryIds);
        }
        return true;
    }

    public PostCloseReasonDTO getCloseReason(String id) {
        Post post = getByIdWithExp(id);
        return PostCloseReasonDTO.builder()
                                 .id(id)
                                 .closeReason(post.getCloseReason())
                                 .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(PostAuditRequest request) {
        Post post = getByIdWithExp(request.getId());
        if (!PostStatusEnum.AUDIT_WAIT.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "该帖子状态异常，无法进行审核操作");
        }

        if (AuditResultEnum.PASS.equals(request.getAuditResult())) {
            post.setStatus(PostStatusEnum.AUDIT_PASS.getCode());
            updateOrInsertAnimalRecord(post);
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

    private void updateOrInsertAnimalRecord(Post post) {
        if (StrUtil.isNotBlank(post.getAnimalId())) {
            Animal animal = animalService.getByIdWithExp(post.getAnimalId());
            if (ObjectUtil.isNull(animal)) {
                throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
            }

            if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())
                    && BizTypeEnum.ADOPT_BIZ.getCode().equals(post.getBizType())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，领养帖无法进行审核通过操作");
            }

            updateAnimalStatus(post, animal);
            animalMapper.updateById(animal);
        } else {
            Animal animal = Animal.builder()
                                  .animalNo(AnimalNoGenerateUtils.generateAnimalNo())
                                  .categoryId(post.getCategoryId())
                                  .ownerId(post.getCreateId())
                                  .name(post.getAnimalName())
                                  .gender(post.getAnimalGender())
                                  .isAdopt(YesOrNoEnum.N.getCode())
                                  .isLost(YesOrNoEnum.N.getCode())
                                  .desc(post.getAnimalDesc())
                                  .build();
            updateAnimalStatus(post, animal);
            animalMapper.insert(animal);
        }
    }

    private void updateAnimalStatus(Post post, Animal animal) {
        if (BizTypeEnum.ADOPT_BIZ.getCode().equals(post.getBizType())) {
            animal.setIsAdopt(YesOrNoEnum.N.getCode());
        }

        if (BizTypeEnum.LOST_BIZ.getCode().equals(post.getBizType())){
            animal.setIsLost(YesOrNoEnum.Y.getCode());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void close(PostCloseRequest request) {
        Post post = getByIdWithExp(request.getId());
        if (PostStatusEnum.CLOSED.getCode().equals(post.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该帖子已被关闭，无需操作");
        }
        post.setStatus(PostStatusEnum.CLOSED.getCode())
            .setCloseReason(request.getCloseReason());
        postMapper.updateById(post);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(PostDeleteRequest request) {
        Post post = getByIdWithExp(request.getId());
        postMapper.deleteByIdWithFill(post);
    }

    private Set<String> getRecurveDownCategoryIds(Collection<String> categoryIds) {
        Set<String> recurveDownCategoryIds = CollUtil.newHashSet();

        if (CollUtil.isEmpty(categoryIds)) {
            return Collections.emptySet();
        }

        for (String id : categoryIds) {
            Set<String> childIds = categoryMapper.recursiveDownCategoryId(id);
            if (CollUtil.isNotEmpty(childIds)) {
                recurveDownCategoryIds.addAll(childIds);
            }
        }

        return CollUtil.isNotEmpty(recurveDownCategoryIds)
                ? recurveDownCategoryIds : Collections.emptySet();
    }

    private Post getByIdWithExp(String id) {
        Post post = postMapper.selectById(id);
        if (ObjectUtil.isNull(post)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return post;
    }

}
