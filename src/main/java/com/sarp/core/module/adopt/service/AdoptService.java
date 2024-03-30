package com.sarp.core.module.adopt.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.adopt.dao.AdoptRecordMapper;
import com.sarp.core.module.adopt.enums.AdoptSearchTypeEnum;
import com.sarp.core.module.adopt.manager.AdoptRecordManager;
import com.sarp.core.module.adopt.model.dto.AdoptAuditDetailDTO;
import com.sarp.core.module.adopt.model.dto.AdoptRecordDetailDTO;
import com.sarp.core.module.adopt.model.entity.AdoptRecord;
import com.sarp.core.module.adopt.model.request.AdoptAuditRequest;
import com.sarp.core.module.adopt.model.request.PlatformAdoptQueryRequest;
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
import com.sarp.core.module.common.enums.YesOrNoEnum;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @date 2024/3/29 17:10
 */

@Service
@Slf4j
@AllArgsConstructor
public class AdoptService {

    private AdoptRecordMapper adoptRecordMapper;
    private AnimalMapper animalMapper;
    private MemberMapper memberMapper;

    private MediaService mediaService;

    private AdoptRecordManager adoptRecordManager;
    private AnimalManager animalManager;
    private MemberManager memberManager;
    private CategoryManager categoryManager;

    public Page<AdoptRecord> platformListPage(PlatformAdoptQueryRequest request) {
        LambdaQueryWrapper<AdoptRecord> queryWrapper = Wrappers.lambdaQuery(AdoptRecord.class)
                                                               .ge(request.getAuditStartTime() != null, AdoptRecord::getAuditTime, request.getAuditStartTime())
                                                               .le(request.getAuditEndTime() != null, AdoptRecord::getAuditTime, request.getAuditEndTime())
                                                               .ge(request.getAdoptStartDate() != null, AdoptRecord::getStartDate, request.getAdoptStartDate())
                                                               .le(request.getAdoptEndDate() != null, AdoptRecord::getEndDate, request.getAdoptEndDate())
                                                               .ge(request.getApplyStartTime() != null, AdoptRecord::getCreateTime, request.getApplyStartTime())
                                                               .le(request.getApplyEndTime() != null, AdoptRecord::getCreateTime, request.getApplyEndTime())
                                                               .orderByDesc(AdoptRecord::getUpdateTime);
        if (request.getStatus() != null) {
            queryWrapper.eq(AdoptRecord::getStatus, request.getStatus());
        }

        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (AdoptSearchTypeEnum.ANIMAL_NAME.equals(request.getSearchType())) {
                List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                          .like(Animal::getName, request.getSearchContent()));
                if (CollUtil.isEmpty(animalList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> animalIds = animalList.stream()
                                                   .map(Animal::getId)
                                                   .collect(Collectors.toList());
                queryWrapper.in(AdoptRecord::getAnimalId, animalIds);
            }
            if (AdoptSearchTypeEnum.ANIMAL_NO.equals(request.getSearchType())) {
                List<Animal> animalList = animalMapper.selectList(Wrappers.lambdaQuery(Animal.class)
                                                                          .like(Animal::getAnimalNo, request.getSearchContent()));
                if (CollUtil.isEmpty(animalList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> animalIds = animalList.stream()
                                                   .map(Animal::getId)
                                                   .collect(Collectors.toList());
                queryWrapper.in(AdoptRecord::getAnimalId, animalIds);
            }
            if (AdoptSearchTypeEnum.ADOPT_USER_NAME.equals(request.getSearchType())) {
                List<Member> memberList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class)
                                                                          .like(Member::getNickName, request.getSearchContent()));
                if (CollUtil.isEmpty(memberList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> userIds = memberList.stream()
                                                 .map(Member::getId)
                                                 .collect(Collectors.toList());
                queryWrapper.in(AdoptRecord::getCreateId, userIds);
            }
            if (AdoptSearchTypeEnum.CONTACT_PHONE.equals(request.getSearchType())) {
                queryWrapper.like(AdoptRecord::getContactPhone, request.getSearchContent());
            }
        }
        return adoptRecordMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public AdoptRecordDetailDTO getRecordDetail(String id) {
        AdoptRecord adoptRecord = adoptRecordManager.getByIdWithExp(id);
        Member adoptUser = memberManager.getByIdWithExp(adoptRecord.getCreateId());
        Animal animal = animalManager.getByIdWithExp(adoptRecord.getAnimalId());
        Category category = categoryManager.getByIdWithExp(animal.getCategoryId());
        AdoptRecordDetailDTO recordDetail = AdoptRecordDetailDTO.builder()
                                                                .id(id)
                                                                .status(adoptRecord.getStatus())
                                                                .animalName(animal.getName())
                                                                .animalNo(animal.getAnimalNo())
                                                                .categoryName(category.getName())
                                                                .adoptUserName(adoptUser.getNickName())
                                                                .adoptUserAccount(adoptUser.getAccount())
                                                                .contactPhone(adoptRecord.getContactPhone())
                                                                .remark(adoptRecord.getRemark())
                                                                .applyTime(adoptRecord.getCreateTime())
                                                                .build();
        if (StrUtil.isNotBlank(animal.getOwnerId())) {
            Member animalOwner = memberManager.getByIdWithExp(animal.getOwnerId());
            recordDetail.setOwnerName(animalOwner.getNickName());
            recordDetail.setOwnerPhone(animalOwner.getPhone());
        }

        List<Media> animalMediaList = mediaService.getMediaList(adoptRecord.getAnimalId(), UploadBizTypeEnum.ANIMAL);
        if (CollUtil.isNotEmpty(animalMediaList)) {
            recordDetail.setPicUrl(animalMediaList.get(0).getPicUrl());
        }

        if (AuditStatusEnum.AUDIT_PASS.getCode().equals(adoptRecord.getStatus())
                || AuditStatusEnum.AUDIT_REJECT.getCode().equals(adoptRecord.getStatus())) {
            if (StrUtil.isBlank(adoptRecord.getAuditId())) {
                throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "审核信息异常");
            }
            recordDetail.setAuditResult(getAuditResultByStatus(adoptRecord.getStatus()));
            Member auditor = memberManager.getByIdWithExp(adoptRecord.getAuditId());
            recordDetail.setAuditUserName(auditor.getNickName());
            recordDetail.setAuditTime(adoptRecord.getAuditTime());
            recordDetail.setAuditRemark(adoptRecord.getAuditRemark());
        }
        return recordDetail;
    }

    public AdoptAuditDetailDTO getAuditDetail(String id) {
        AdoptRecord adoptRecord = adoptRecordManager.getByIdWithExp(id);
        Member adoptUser = memberManager.getByIdWithExp(adoptRecord.getCreateId());
        Animal animal = animalManager.getByIdWithExp(adoptRecord.getAnimalId());
        Category category = categoryManager.getByIdWithExp(animal.getCategoryId());
        AdoptAuditDetailDTO detail = AdoptAuditDetailDTO.builder()
                                                        .id(id)
                                                        .animalName(animal.getName())
                                                        .animalNo(animal.getAnimalNo())
                                                        .categoryName(category.getName())
                                                        .isAdopt(animal.getIsAdopt())
                                                        .isLost(animal.getIsLost())
                                                        .adoptUserName(adoptUser.getNickName())
                                                        .contactPhone(adoptRecord.getContactPhone())
                                                        .remark(adoptRecord.getRemark())
                                                        .build();
        if (StrUtil.isNotBlank(animal.getOwnerId())) {
            Member animalOwner = memberManager.getByIdWithExp(animal.getOwnerId());
            detail.setOwnerName(animalOwner.getNickName());
            detail.setOwnerPhone(animalOwner.getPhone());
        }
        if (AuditStatusEnum.AUDIT_PASS.getCode().equals(adoptRecord.getStatus())
                || AuditStatusEnum.AUDIT_REJECT.getCode().equals(adoptRecord.getStatus())) {
            if (StrUtil.isBlank(adoptRecord.getAuditId())) {
                throw new BizException(HttpResultCode.BIZ_DATA_EXCEPTION, "审核信息异常");
            }
            detail.setAuditResult(getAuditResultByStatus(adoptRecord.getStatus()));
            Member auditor = memberManager.getByIdWithExp(adoptRecord.getAuditId());
            detail.setAuditUserName(auditor.getNickName());
            detail.setAuditTime(adoptRecord.getAuditTime());
            detail.setAuditRemark(adoptRecord.getAuditRemark());
        }
        return detail;
    }

    private String getAuditResultByStatus(Integer status) {
        if (AuditStatusEnum.AUDIT_PASS.getCode().equals(status)) {
            return AuditResultEnum.PASS.name();
        } else if (AuditStatusEnum.AUDIT_REJECT.getCode().equals(status)) {
            return AuditResultEnum.REJECT.name();
        } else {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void audit(AdoptAuditRequest request) {
        AdoptRecord adoptRecord = adoptRecordManager.getByIdWithExp(request.getId());
        if (AuditResultEnum.PASS.equals(request.getAuditResult())) {
            adoptRecord.setStatus(AuditStatusEnum.AUDIT_PASS.getCode())
                       .setStartDate(DateUtil.date())
                       .setAuditRemark(request.getAuditRemark());

            Animal animal = animalManager.getByIdWithExp(adoptRecord.getAnimalId());
            animal.setOwnerId(adoptRecord.getCreateId())
                  .setIsAdopt(YesOrNoEnum.Y.getCode());
            animalMapper.updateById(animal);
        } else {
            adoptRecord.setStatus(AuditStatusEnum.AUDIT_REJECT.getCode());
            if (StrUtil.isBlank(request.getAuditRemark())) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "审核备注不能为空");
            }
            adoptRecord.setAuditRemark(request.getAuditRemark());
        }
        adoptRecord.setAuditId(ContextUtils.getCurrentUserId())
                   .setAuditTime(DateUtil.date());
        adoptRecordMapper.updateById(adoptRecord);
    }

}
