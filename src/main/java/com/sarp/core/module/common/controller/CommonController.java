package com.sarp.core.module.common.controller;

import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.dto.CategoryTreeDTO;
import com.sarp.core.module.common.model.dto.UserListDTO;
import com.sarp.core.module.common.service.CommonService;
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
 * @date 2024/3/7 9:24
 */

@Api(tags = "公共模块-公共接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commonModule/common")
public class CommonController {

    private CommonService commonService;

    @ApiOperation(value = "获取动物类目树")
    @GetMapping("/getCategoryTree")
    public HttpResult<CategoryTreeDTO> getCategoryTree() {
        return HttpResult.success(commonService.getCategoryTree());
    }

    @ApiOperation(value = "获取宠物主人下拉列表")
    @GetMapping("/getAnimalOwnerList")
    public HttpResult<List<UserListDTO>> getAnimalOwnerList() {
        return HttpResult.success(JavaBeanUtils.mapList(commonService.getAnimalOwnerList(), UserListDTO.class));
    }

}
