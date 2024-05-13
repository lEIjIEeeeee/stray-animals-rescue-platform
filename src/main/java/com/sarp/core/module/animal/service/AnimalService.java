package com.sarp.core.module.animal.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.adopt.dao.AdoptRecordMapper;
import com.sarp.core.module.adopt.model.entity.AdoptRecord;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.enums.AnimalSearchTypeEnum;
import com.sarp.core.module.animal.enums.AuditStatusEnum;
import com.sarp.core.module.animal.helper.AnimalHelper;
import com.sarp.core.module.animal.manager.AnimalManager;
import com.sarp.core.module.animal.model.dto.*;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.model.request.*;
import com.sarp.core.module.animal.util.AnimalNoGenerateUtils;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.auth.service.MemberService;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.UploadBizTypeEnum;
import com.sarp.core.module.common.enums.YesOrNoEnum;
import com.sarp.core.module.contribution.dao.ContributionRecordMapper;
import com.sarp.core.module.contribution.model.entity.ContributionRecord;
import com.sarp.core.module.media.dao.MediaMapper;
import com.sarp.core.module.media.model.entity.Media;
import com.sarp.core.module.media.service.MediaService;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.enums.UserTypeEnum;
import com.sarp.core.module.user.model.entity.Member;
import com.sarp.core.util.JavaBeanUtils;
import com.sarp.core.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2024/1/30 17:24
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnimalService {

    private AnimalMapper animalMapper;
    private CategoryMapper categoryMapper;
    private MemberMapper memberMapper;
    private MediaMapper mediaMapper;
    private AdoptRecordMapper adoptRecordMapper;
    private ContributionRecordMapper contributionRecordMapper;

    private CategoryService categoryService;
    private MediaService mediaService;
    private MemberService memberService;

    private AnimalHelper animalHelper;

    private AnimalManager animalManager;

    public Page<Animal> listPage(AnimalQueryRequest request) {
        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class)
                                                          .and(StrUtil.isNotBlank(request.getSearchContent()),
                                                                  wrapper -> wrapper.or(wp -> wp.like(Animal::getName, request.getSearchContent()))
                                                                                    .or(wp -> wp.like(Animal::getAnimalNo, request.getSearchContent()))
                                                          )
                                                          .eq(request.getIsAdopt() != null, Animal::getIsAdopt, request.getIsAdopt())
                                                          .eq(request.getIsLost() != null, Animal::getIsLost, request.getIsLost())
                                                          .orderByDesc(Animal::getUpdateTime);
        if (StrUtil.isNotBlank(request.getCategoryIds())) {
            List<String> categoryIds = CollUtil.newArrayList(StrUtil.split(request.getCategoryIds(), StrUtil.COMMA));
            Set<String> categoryIdSet = categoryService.getRecursiveCategoryIds(categoryIds);
            if (CollUtil.isNotEmpty(categoryIdSet)) {
                queryWrapper.in(Animal::getCategoryId, categoryIdSet);
            }
        }
        return animalMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public Page<Animal> listPagePlatform(PlatformAnimalQueryRequest request) {
        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class)
                                                          .ge(request.getCreateStartDate() != null, Animal::getCreateTime, request.getCreateStartDate())
                                                          .le(request.getCreateEndDate() != null, Animal::getCreateTime, request.getCreateEndDate())
//                                                          .orderByDesc(Animal::getAnimalNo)
                                                          .orderByDesc(Animal::getUpdateTime);
        if (request.getSearchType() != null
                && StrUtil.isNotBlank(request.getSearchContent())) {
            if (AnimalSearchTypeEnum.ANIMAL_NAME.equals(request.getSearchType())) {
                queryWrapper.like(Animal::getName, request.getSearchContent());
            }
            if (AnimalSearchTypeEnum.ANIMAL_NO.equals(request.getSearchType())) {
                queryWrapper.like(Animal::getAnimalNo, request.getSearchContent());
            }
            if (AnimalSearchTypeEnum.OWNER_NAME.equals(request.getSearchType())) {
                List<Member> memberList = memberMapper.selectList(Wrappers.lambdaQuery(Member.class)
                                                                          .like(Member::getNickName, request.getSearchContent()));
                if (CollUtil.isEmpty(memberList)) {
                    return PageUtils.createEmptyPage();
                }
                List<String> userIds = memberList.stream()
                                                 .map(Member::getId)
                                                 .collect(Collectors.toList());
                queryWrapper.in(Animal::getCreateId, userIds);
            }
        }

        if (request.getGender() != null) {
            queryWrapper.eq(Animal::getGender, request.getGender().name());
        }
        if (CollUtil.isNotEmpty(request.getCategoryIds())) {
            Set<String> recurveDownCategoryIds = categoryService.getRecursiveCategoryIds(request.getCategoryIds());
            if (CollUtil.isEmpty(recurveDownCategoryIds)) {
                return PageUtils.createEmptyPage();
            }
            queryWrapper.in(Animal::getCategoryId, recurveDownCategoryIds);
        }
        if (request.getIsAdopt() != null) {
            queryWrapper.eq(Animal::getIsAdopt, request.getIsAdopt());
        }
        if (request.getIsLost() != null) {
            queryWrapper.eq(Animal::getIsLost, request.getIsLost());
        }
        return animalMapper.selectPage(PageUtils.createPage(request), queryWrapper);
    }

    public AnimalDetailDTO getDetail(String id) {
        Animal animal = animalManager.getByIdWithExp(id);
        return getAnimalDetail(animal, AnimalDetailDTO.class);
    }

    public PlatformAnimalDetailDTO getPlatformDetail(String id) {
        Animal animal = animalManager.getByIdWithExp(id);
        PlatformAnimalDetailDTO animalDetail = getAnimalDetail(animal, PlatformAnimalDetailDTO.class);

        List<AdoptRecord> adoptRecordList = adoptRecordMapper.selectList(Wrappers.lambdaQuery(AdoptRecord.class)
                                                                                 .eq(AdoptRecord::getAnimalId, id)
                                                                                 .eq(AdoptRecord::getStatus, AuditStatusEnum.AUDIT_PASS.getCode())
                                                                                 .orderByDesc(AdoptRecord::getAuditTime));
        if (CollUtil.isNotEmpty(adoptRecordList)) {
            List<AnimalAdoptRecordDTO> adoptRecordDtoList =
                    adoptRecordList.stream()
                                   .map(record -> {
                                       AnimalAdoptRecordDTO recordDTO = JavaBeanUtils.map(record, AnimalAdoptRecordDTO.class);
                                       recordDTO.setAdoptUserId(record.getCreateId());
                                       return recordDTO;
                                   })
                                   .collect(Collectors.toList());
            fillAdoptRecordListData(adoptRecordDtoList);
            animalDetail.setAdoptRecordList(adoptRecordDtoList);
        }

        List<ContributionRecord> contributionRecordList = contributionRecordMapper.selectList(Wrappers.lambdaQuery(ContributionRecord.class)
                                                                                                      .eq(ContributionRecord::getAnimalId, id)
                                                                                                      .eq(ContributionRecord::getStatus, AuditStatusEnum.AUDIT_PASS.getCode())
                                                                                                      .orderByDesc(ContributionRecord::getAuditTime));
        if (CollUtil.isNotEmpty(contributionRecordList)) {
            List<AnimalContributionRecordDTO> contributionRecordDtoList = contributionRecordList.stream()
                                                                                                .map(record -> {
                                                                                                    AnimalContributionRecordDTO recordDTO = JavaBeanUtils.map(record, AnimalContributionRecordDTO.class);
                                                                                                    recordDTO.setApplyUserId(record.getCreateId());
                                                                                                    return recordDTO;
                                                                                                })
                                                                                                .collect(Collectors.toList());
            fillContributionRecordListData(contributionRecordDtoList);
            animalDetail.setContributionRecordList(contributionRecordDtoList);
        }
        return animalDetail;
    }

    private void fillAdoptRecordListData(List<AnimalAdoptRecordDTO> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> userIds = CollUtil.newHashSet();
        userIds.addAll(dataList.stream()
                               .map(AnimalAdoptRecordDTO::getAdoptUserId)
                               .collect(Collectors.toSet()));
        userIds.addAll(dataList.stream()
                               .map(AnimalAdoptRecordDTO::getAuditId)
                               .collect(Collectors.toSet()));

        Map<String, Member> memberMap = memberService.getMemberMap(userIds);
        if (CollUtil.isEmpty(memberMap)) {
            return;
        }

        for (AnimalAdoptRecordDTO record : dataList) {
            Member adoptUser = memberMap.get(record.getAdoptUserId());
            if (ObjectUtil.isNotNull(adoptUser)) {
                record.setAdoptUserName(adoptUser.getNickName());
                record.setAdoptUserAccount(adoptUser.getAccount());
            }

            Member auditor = memberMap.get(record.getAuditId());
            if (ObjectUtil.isNotNull(auditor)) {
                record.setAuditorName(auditor.getNickName());
                record.setAuditorPhone(auditor.getPhone());
            }
        }
    }

    private void fillContributionRecordListData(List<AnimalContributionRecordDTO> dataList) {
        if (CollUtil.isEmpty(dataList)) {
            return;
        }
        Set<String> userIds = CollUtil.newHashSet();
        userIds.addAll(dataList.stream()
                               .map(AnimalContributionRecordDTO::getApplyUserId)
                               .collect(Collectors.toSet()));
        userIds.addAll(dataList.stream()
                               .map(AnimalContributionRecordDTO::getAuditId)
                               .collect(Collectors.toSet()));

        Map<String, Member> memberMap = memberService.getMemberMap(userIds);
        if (CollUtil.isEmpty(memberMap)) {
            return;
        }

        for (AnimalContributionRecordDTO record : dataList) {
            Member applyUser = memberMap.get(record.getApplyUserId());
            if (ObjectUtil.isNotNull(applyUser)) {
                record.setApplyUserName(applyUser.getNickName());
                record.setApplyUserAccount(applyUser.getAccount());
            }

            Member auditor = memberMap.get(record.getAuditId());
            if (ObjectUtil.isNotNull(auditor)) {
                record.setAuditorName(auditor.getNickName());
                record.setAuditorPhone(auditor.getPhone());
            }
        }
    }

    private <T> T getAnimalDetail(Animal animal, Class<T> animalDetailClass) {
        if (!animalDetailClass.equals(AnimalDetailDTO.class)
                && !animalDetailClass.equals(PlatformAnimalDetailDTO.class)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "对象类型异常");
        }
        AnimalBaseInfoDTO baseInfo = JavaBeanUtils.map(animal, AnimalBaseInfoDTO.class);
        animalHelper.fillAnimalBaseInfo(baseInfo);
        return JavaBeanUtils.map(baseInfo, animalDetailClass);
    }

    @Transactional(rollbackFor = Exception.class)
    public void applyAdopt(AdoptApplyRequest request) {
        String userId = ContextUtils.getCurrentUserId();
        Animal animal = animalManager.getByIdWithExp(request.getAnimalId());
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsAdopt())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已被领养，无法领养");
        }
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，无法领养");
        }
        if (ObjectUtil.equal(animal.getOwnerId(), userId)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "您已领养当前宠物，无需再次领养");
        }
        AdoptRecord waitAuditRecord = adoptRecordMapper.selectOne(Wrappers.lambdaQuery(AdoptRecord.class)
                                                                          .eq(AdoptRecord::getAnimalId, request.getAnimalId())
                                                                          .eq(AdoptRecord::getCreateId, userId)
                                                                          .eq(AdoptRecord::getStatus, AuditStatusEnum.AUDIT_WAIT.getCode()));
        if (ObjectUtil.isNotNull(waitAuditRecord)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "您已提交当前宠物的领养申请，无需重复操作");
        }

        AdoptRecord adoptRecord = AdoptRecord.builder()
                                             .animalId(animal.getId())
                                             .contactPhone(request.getContactPhone())
                                             .status(AuditStatusEnum.AUDIT_WAIT.getCode())
                                             .remark(request.getRemark())
                                             .build();
        adoptRecordMapper.insert(adoptRecord);
    }

    @Transactional(rollbackFor = Exception.class)
    public void applyContribution(ContributionApplyRequest request) {
        Animal animal = animalManager.getByIdWithExp(request.getAnimalId());
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，无法捐助");
        }
        if (ObjectUtil.equal(animal.getOwnerId(), ContextUtils.getCurrentUserId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "您已领养当前宠物，无需捐助");
        }

        ContributionRecord contributionRecord = JavaBeanUtils.map(request, ContributionRecord.class);
        contributionRecord.setStatus(AuditStatusEnum.AUDIT_WAIT.getCode())
                          .setItemType(request.getItemType().getCode());
        contributionRecordMapper.insert(contributionRecord);

        Media contributionMedia = Media.builder()
                                       .serviceId(contributionRecord.getId())
                                       .serviceType(UploadBizTypeEnum.CONTRIBUTION.name())
                                       .picUrl(request.getItemPic())
                                       .sort(1)
                                       .build();
        mediaMapper.insert(contributionMedia);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(PlatformAnimalAddRequest request) {
        Animal animal = JavaBeanUtils.map(request, Animal.class);
        animal.setAnimalNo(AnimalNoGenerateUtils.generateAnimalNo())
              .setGender(request.getGender().name());
        animalMapper.insert(animal);
        insertOrUpdateAnimalMediaInfo(animal, CollUtil.newArrayList(request.getPicUrl()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(PlatformAnimalEditRequest request) {
        Animal animal = animalManager.getByIdWithExp(request.getId());

        if (YesOrNoEnum.Y.getCode().equals(animal.getIsAdopt())
                && StrUtil.isBlank(request.getOwnerId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该宠物为已领养状态，当前主人不能为空");
        }

        JavaBeanUtils.map(request, animal);
        if (request.getGender() != null) {
            animal.setGender(request.getGender().name());
        }
        animalMapper.updateById(animal);
        insertOrUpdateAnimalMediaInfo(animal, CollUtil.newArrayList(request.getPicUrl()));
    }

    private void insertOrUpdateAnimalMediaInfo(Animal animal, List<String> mediaList) {
        if (CollUtil.isEmpty(mediaList)) {
            return;
        }
        List<Media> animalMediaList = mediaService.getMediaList(animal.getId(), UploadBizTypeEnum.ANIMAL);
        if (CollUtil.isNotEmpty(animalMediaList)) {
            List<String> mediaIds = animalMediaList.stream()
                                                   .map(Media::getId)
                                                   .collect(Collectors.toList());
            mediaMapper.deleteBatchWithFill(Media.builder()
                                                 .build(), Wrappers.lambdaQuery(Media.class)
                                                                   .in(Media::getId, mediaIds));
        }

        List<Media> mediaListNew = CollUtil.newArrayList();
        mediaList.forEach(media -> {
            mediaListNew.add( Media.builder()
                                   .serviceId(animal.getId())
                                   .serviceType(UploadBizTypeEnum.ANIMAL.name())
                                   .picUrl(media)
                                   .sort(mediaList.indexOf(media) + 1)
                                   .build());
        });

        if (CollUtil.isEmpty(mediaListNew)) {
            return;
        }
        mediaMapper.insertBatchSomeColumn(mediaListNew);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(AnimalDeleteRequest request) {
        Animal animal = animalManager.getByIdWithExp(request.getId());
        animalMapper.deleteByIdWithFill(animal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeAdoptStatus(ChangeAdoptStatusRequest request) {
        Animal animal = animalManager.getByIdWithExp(request.getId());
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，无法更改领养状态");
        }
        animal.setIsAdopt(request.getIsAdopt());
        animalMapper.updateById(animal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeLostStatus(ChangeLostStatusRequest request) {
        Animal animal = animalManager.getByIdWithExp(request.getId());
        animal.setIsLost(request.getIsLost());
        animalMapper.updateById(animal);
    }

    public List<Animal> getAnimalListByCategoryId(AnimalSelectRequest request) {
        LoginUser user = ContextUtils.getCurrentUser();

        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class);
        if (UserTypeEnum.NORMAL_USER.name().equals(user.getUserType())) {
            queryWrapper.eq(Animal::getOwnerId, user.getId());
        }

        if (StrUtil.isNotBlank(request.getCategoryId())) {
            Set<String> categoryIds = categoryMapper.recursiveDownCategoryId(request.getCategoryId());
            queryWrapper.eq(CollUtil.isEmpty(categoryIds), Animal::getCategoryId, request.getCategoryId());
            queryWrapper.in(CollUtil.isNotEmpty(categoryIds), Animal::getCategoryId, categoryIds);
        }
        return animalMapper.selectList(queryWrapper);
    }

}
