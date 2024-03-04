package com.sarp.core.module.animal.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.context.ContextUtils;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.model.request.AnimalSelectRequest;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.category.dao.CategoryMapper;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.user.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @date 2024/1/30 17:24
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnimalService {

    private AnimalMapper animalMapper;
    private CategoryMapper categoryMapper;

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
