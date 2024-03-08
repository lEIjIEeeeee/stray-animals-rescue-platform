package com.sarp.core.module.animal.controller;

import com.sarp.core.module.animal.model.dto.AnimalSelectListDTO;
import com.sarp.core.module.animal.model.request.AnimalSelectRequest;
import com.sarp.core.module.animal.service.AnimalService;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @date 2024/3/3 23:07
 */

@Api(tags = "用户端宠物模块-流浪动物相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/animalModule/animal")
public class AnimalController {

    private AnimalService animalService;

    @ApiOperation(value = "发帖时获取宠物下拉列表")
    @GetMapping("/getAnimalListByCategoryId")
    public HttpResult<List<AnimalSelectListDTO>> getAnimalListByCategoryId(AnimalSelectRequest request) {
        List<AnimalSelectListDTO> animalSelectList = JavaBeanUtils.mapList(animalService.getAnimalListByCategoryId(request), AnimalSelectListDTO.class);
        return HttpResult.success(animalSelectList);
    }

}
