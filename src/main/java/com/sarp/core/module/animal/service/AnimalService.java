package com.sarp.core.module.animal.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.animal.model.request.AnimalSelectRequest;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @date 2024/1/30 17:24
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnimalService {

    private AnimalMapper animalMapper;

    public List<Animal> getAnimalSelectList(AnimalSelectRequest request) {
        LambdaQueryWrapper<Animal> queryWrapper = Wrappers.lambdaQuery(Animal.class);
        queryWrapper.eq(StrUtil.isNotBlank(request.getCategoryId()), Animal::getCategoryId, request.getCategoryId());
        queryWrapper.and(StrUtil.isNotBlank(request.getSearchContent()),
                wrapper -> wrapper.or(wp -> wp.like(Animal::getAnimalNo, request.getSearchContent()))
                                  .or(wp -> wp.like(Animal::getName, request.getSearchContent())));
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
