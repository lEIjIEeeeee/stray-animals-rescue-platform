package com.sarp.core.module.contribution.service;

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
import com.sarp.core.module.animal.enums.AuditStatusEnum;
import com.sarp.core.module.animal.manager.AnimalManager;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.auth.manager.MemberManager;
import com.sarp.core.module.category.manager.CategoryManager;
import com.sarp.core.module.category.model.entity.Category;
import com.sarp.core.module.common.enums.AuditResultEnum;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.contribution.dao.ContributionRecordMapper;
import com.sarp.core.module.contribution.enums.ContributionSearchTypeEnum;
import com.sarp.core.module.contribution.manager.ContributionManager;
import com.sarp.core.module.contribution.model.dto.ContributionAuditDetailDTO;
import com.sarp.core.module.contribution.model.dto.ContributionRecordDetailDTO;
import com.sarp.core.module.contribution.model.entity.ContributionRecord;
import com.sarp.core.module.contribution.model.request.ContributionAuditRequest;
import com.sarp.core.module.contribution.model.request.PlatformContributionQueryRequest;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2024/3/30 21:22
 */

@Service
@Slf4j
@AllArgsConstructor
public class ContributionService {

    private ContributionRecordMapper contributionRecordMapper;
    private AnimalMapper animalMapper;
    private MemberMapper memberMapper;

    private ContributionManager contributionManager;
    private AnimalManager animalManager;
    private CategoryManager categoryManager;
    private MemberManager memberManager;

    private MediaService mediaService;

    public Page<ContributionRecord> platformListPage(PlatformContributionQueryRequest request) {
        LambdaQueryWrapper<ContributionRecord> queryWrapper = Wrappers.lambdaQuery(ContributionRecord.class)
                                                                      .ge(request.getAuditStartTime() != null, ContributionRecord::getAuditTime, request.getApplyStartTime())
                                                                      .le(request.getAuditEndTime() != null, ContributionRecord::getAuditTime, request.getAuditEndTime())
                                                                      .ge(request.getApplyStartTime() != null, ContributionRecord::getCreateTime, request.getApplyStartTime())
                                                                      .le(request.getApplyEndTime() != null, ContributionRecord::getCreateTime, request.getApplyEndTime())
                                                                      .orderByDesc(ContributionRecord::getCreateTime)
                                                                      .orderByDesc(ContributionRecord::getUpdateTime);
        if (request.getItemType() != null) {
            queryWrapper.eq(ContributionRecord::getItemType, request.getItemType().getCode());
        }
        if (request.getStatus() != null) {
            queryWrapper.eq(ContributionRecord::getStatus, request.getStatus().getCode());
        }
        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (ContributionSearchTypeEnum.ITEM_NAME.equals(request.getSearchType())) {
                queryWrapper.like(ContributionRecord::getItemName, request.getSearchContent());
            }
            if (ContributionSearchTypeEnum.CONTACT_PHONE.equals(request.getSearchType())) {
                queryWrapper.like(ContributionRecord::getContactPhone, request.getSearchContent());
            }
            if (ContributionSearchTypeEnum.ANIMAL_NAME.equals(request.getSearchType())) {
                List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                          .like(Animal::getName, request.getSearchContent()));
                if (CollUtil.isEmpty(animalList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> animalIds = animalList.stream()
                                                   .map(Animal::getId)
                                                   .collect(Collectors.toList());
                queryWrapper.in(ContributionRecord::getAnimalId, animalIds);
            }
            if (ContributionSearchTypeEnum.ANIMAL_NO.equals(request.getSearchType())) {
                List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                          .like(Animal::getAnimalNo, request.getSearchContent()));
                if (CollUtil.isEmpty(animalList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> animalIds = animalList.stream()
                                                   .map(Animal::getId)
                                                   .collect(Collectors.toList());
                queryWrapper.in(ContributionRecord::getAnimalId, animalIds);
            }
            if (ContributionSearchTypeEnum.APPLY_USER_NAME.equals(request.getSearchType())) {
                List<Member> applyUserList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class)
                                                                             .like(Member::getNickName, request.getSearchContent()));
                if (CollUtil.isEmpty(applyUserList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> applyUserIds = applyUserList.stream()
                                                         .map(Member::getId)
                                                         .collect(Collectors.toList());
                queryWrapper.in(ContributionRecord::getCreateId, applyUserIds);
            }
        }
        return contributionRecordMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public ContributionRecordDetailDTO getRecordDetail(String id) {
        ContributionRecord contributionRecord = contributionManager.getByIdWithExp(id);
        ContributionRecordDetailDTO recordDetail = JavaBeanUtils.map(contributionRecord, ContributionRecordDetailDTO.class);
        recordDetail.setApplyTime(contributionRecord.getCreateTime());

        Animal animal = animalManager.getByIdWithExp(contributionRecord.getAnimalId());
        recordDetail.setAnimalName(animal.getName());
        recordDetail.setAnimalNo(animal.getAnimalNo());

        Category category = categoryManager.getByIdWithExp(animal.getCategoryId());
        recordDetail.setCategoryName(category.getName());

        Member applyUser = memberManager.getByIdWithExp(contributionRecord.getCreateId());
        recordDetail.setApplyUserName(applyUser.getNickName());
        recordDetail.setApplyUserAccount(applyUser.getAccount());

        if (StrUtil.isNotBlank(animal.getOwnerId())) {
            Member owner = memberManager.getByIdWithExp(animal.getOwnerId());
            recordDetail.setOwnerName(owner.getNickName());
            recordDetail.setOwnerPhone(owner.getPhone());
        }

        if (StrUtil.isNotBlank(contributionRecord.getAuditId())) {
            Member auditor = memberManager.getByIdWithExp(contributionRecord.getAuditId());
            recordDetail.setAuditUserName(auditor.getNickName());
        }

        List<Media> animalMediaList = mediaService.getMediaList(contributionRecord.getAnimalId(), UploadBizTypeEnum.ANIMAL);
        if (CollUtil.isNotEmpty(animalMediaList)) {
            recordDetail.setPicUrl(animalMediaList.get(0).getPicUrl());
        }
        return recordDetail;
    }

    public ContributionAuditDetailDTO getAuditDetail(String id) {
        ContributionRecord contributionRecord = contributionManager.getByIdWithExp(id);
        ContributionAuditDetailDTO auditDetail = JavaBeanUtils.map(contributionRecord, ContributionAuditDetailDTO.class);

        Animal animal = animalManager.getByIdWithExp(contributionRecord.getAnimalId());
        auditDetail.setAnimalName(animal.getName());
        auditDetail.setAnimalNo(animal.getAnimalNo());

        Category category = categoryManager.getByIdWithExp(animal.getCategoryId());
        auditDetail.setCategoryName(category.getName());

        Member applyUser = memberManager.getByIdWithExp(contributionRecord.getCreateId());
        auditDetail.setApplyUserName(applyUser.getNickName());

        if (StrUtil.isNotBlank(animal.getOwnerId())) {
            Member owner = memberManager.getByIdWithExp(animal.getOwnerId());
            auditDetail.setOwnerName(owner.getNickName());
            auditDetail.setOwnerPhone(owner.getPhone());
        }
        if (StrUtil.isNotBlank(contributionRecord.getAuditId())) {
            Member auditor = memberManager.getByIdWithExp(contributionRecord.getAuditId());
            auditDetail.setAuditUserName(auditor.getNickName());
        }
        return auditDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(ContributionAuditRequest request) {
        ContributionRecord contributionRecord = contributionManager.getByIdWithExp(request.getId());
        if (ObjectUtil.notEqual(contributionRecord.getStatus(), AuditStatusEnum.AUDIT_WAIT.getCode())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "审核状态异常");
        }
        if (AuditResultEnum.PASS.equals(request.getAuditResult())) {
            contributionRecord.setStatus(AuditStatusEnum.AUDIT_PASS.getCode());
        } else {
            if (StrUtil.isBlank(request.getAuditRemark())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "审核拒绝时备注不能为空");
            }
            contributionRecord.setStatus(AuditStatusEnum.AUDIT_REJECT.getCode());
        }
        contributionRecord.setAuditId(ContextUtils.getCurrentUserId())
                          .setAuditTime(DateUtil.date())
                          .setAuditRemark(request.getAuditRemark());
        contributionRecordMapper.updateById(contributionRecord);
    }

}
