package com.sarp.core.module.animal.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.enums.AnimalSearchTypeEnum;
import com.sarp.core.module.animal.helper.AnimalHelper;
import com.sarp.core.module.animal.model.dto.PlatformAnimalDetailDTO;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.model.request.*;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.category.service.CategoryService;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.enums.YesOrNoEnum;
import com.sarp.core.module.user.dao.MemberMapper;
import com.sarp.core.module.user.enums.UserTypeEnum;
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
 * @date 2024/1/30 17:24
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnimalService {

    private AnimalMapper animalMapper;
    private CategoryMapper categoryMapper;
    private MemberMapper memberMapper;

    private AnimalHelper animalHelper;

    private CategoryService categoryService;

    public Page<Animal> listPagePlatform(PlatformAnimalQueryRequest request) {
        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class)
                                                          .ge(request.getCreateStartDate() != null, Animal::getCreateTime, request.getCreateStartDate())
                                                          .le(request.getCreateEndDate() != null, Animal::getCreateTime, request.getCreateEndDate())
                                                          .orderByDesc(Animal::getAnimalNo)
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

    public PlatformAnimalDetailDTO get(String id) {
        Animal animal = getByIdWithExp(id);
        PlatformAnimalDetailDTO animalDetail = JavaBeanUtils.map(animal, PlatformAnimalDetailDTO.class);
        animalHelper.fillAnimalDetailData(animalDetail);
        return animalDetail;
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(PlatformAnimalEditRequest request) {
        Animal animal = getByIdWithExp(request.getId());

        if (YesOrNoEnum.Y.getCode().equals(animal.getIsAdopt())
                && StrUtil.isBlank(request.getOwnerId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该宠物为已领养状态，当前主人不能为空");
        }

        JavaBeanUtils.map(request, animal);
        if (request.getGender() != null) {
            animal.setGender(request.getGender().name());
        }
        animalMapper.updateById(animal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(AnimalDeleteRequest request) {
        Animal animal = getByIdWithExp(request.getId());
        animalMapper.deleteByIdWithFill(animal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeAdoptStatus(ChangeAdoptStatusRequest request) {
        Animal animal = getByIdWithExp(request.getId());
        if (YesOrNoEnum.Y.getCode().equals(animal.getIsLost())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "当前宠物已遗失，无法更改领养状态");
        }
        animal.setIsAdopt(request.getIsAdopt());
        animalMapper.updateById(animal);
    }

    @Transactional(rollbackFor = Exception.class)
    public void changeLostStatus(ChangeLostStatusRequest request) {
        Animal animal = getByIdWithExp(request.getId());
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

    public Animal getByIdWithExp(String id) {
        Animal animal = animalMapper.selectById(id);
        if (ObjectUtil.isNull(animal)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return animal;
    }

}
