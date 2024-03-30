package com.sarp.core.module.animal.controller;

import com.sarp.core.module.animal.helper.AnimalHelper;
import com.sarp.core.module.animal.model.dto.PlatformAnimalDetailDTO;
import com.sarp.core.module.animal.model.request.*;
import com.sarp.core.module.animal.model.response.PlatformAnimalResponse;
import com.sarp.core.module.animal.service.AnimalService;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/3 18:23
 */

@Api(tags = "平台端宠物模块-流浪动物相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/animalModule/platform/animal")
public class PlatformAnimalController {

    private AnimalService animalService;

    private AnimalHelper animalHelper;

    @ApiOperation(value = "分页查询宠物信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformAnimalResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                       PlatformAnimalQueryRequest request) {
        PageVO<PlatformAnimalResponse> responsePageVO = CommonConvert.convertPageToPageVo(animalService.listPagePlatform(request), PlatformAnimalResponse.class);
        animalHelper.fillPlatformAnimalListData(responsePageVO.getDataList());
        return HttpResult.success(responsePageVO);
    }

    @ApiOperation(value = "查询宠物详情")
    @GetMapping("/get")
    public HttpResult<PlatformAnimalDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(animalService.getPlatformDetail(id));
    }

    @ApiOperation(value = "新增宠物信息")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated PlatformAnimalAddRequest request) {
        animalService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑宠物信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated PlatformAnimalEditRequest request) {
        animalService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新宠物领养状态")
    @PostMapping("/changeAdoptStatus")
    public HttpResult<Void> changeAdoptStatus(@RequestBody @Validated ChangeAdoptStatusRequest request) {
        animalService.changeAdoptStatus(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "更新宠物遗失状态")
    @PostMapping("/changeLostStatus")
    public HttpResult<Void> changeLostStatus(@RequestBody @Validated ChangeLostStatusRequest request) {
        animalService.changeLostStatus(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除宠物信息")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated AnimalDeleteRequest request) {
        animalService.delete(request);
        return HttpResult.success();
    }

}
