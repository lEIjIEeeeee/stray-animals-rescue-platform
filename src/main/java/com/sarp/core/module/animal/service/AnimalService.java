package com.sarp.core.module.animal.service;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @date 2024/1/30 17:24
 */

@Service
@Slf4j
@AllArgsConstructor
public class AnimalService {

    private AnimalMapper animalMapper;

    public Animal getByIdWithExp(String id) {
        Animal animal = animalMapper.selectById(id);
        if (ObjectUtil.isNull(animal)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return animal;
    }

}
