package com.sarp.core.module.animal.manager;

import cn.hutool.core.util.ObjectUtil;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.animal.dao.AnimalMapper;
import com.sarp.core.module.animal.model.entity.Animal;
import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @date 2024/3/19 10:26
 */

@Component
@AllArgsConstructor
public class AnimalManager {

    private AnimalMapper animalMapper;

    public Animal getByIdWithExp(String id) {
        Animal animal = animalMapper.selectById(id);
        if (ObjectUtil.isNull(animal)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTED);
        }
        return animal;
    }

}
