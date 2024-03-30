package com.sarp.core.module.animal.controller;

import com.sarp.core.module.animal.helper.AnimalHelper;
import com.sarp.core.module.animal.model.dto.AnimalDetailDTO;
import com.sarp.core.module.animal.model.dto.AnimalSelectListDTO;
import com.sarp.core.module.animal.model.request.AdoptApplyRequest;
import com.sarp.core.module.animal.model.request.AnimalQueryRequest;
import com.sarp.core.module.animal.model.request.AnimalSelectRequest;
import com.sarp.core.module.animal.model.request.ContributionApplyRequest;
import com.sarp.core.module.animal.model.response.AnimalResponse;
import com.sarp.core.module.animal.service.AnimalService;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
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

    private AnimalHelper animalHelper;

    @ApiOperation(value = "分页查询宠物信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<AnimalResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                               AnimalQueryRequest request) {
        PageVO<AnimalResponse> animalResponsePageVO = CommonConvert.convertPageToPageVo(animalService.listPage(request), AnimalResponse.class);
        animalHelper.fillAnimalListData(animalResponsePageVO.getDataList());
        animalHelper.fillAnimalListMediaData(animalResponsePageVO.getDataList());
        return HttpResult.success(animalResponsePageVO);
    }

    @ApiOperation(value = "查询宠物信息详情")
    @GetMapping("/get")
    public HttpResult<AnimalDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(animalService.getDetail(id));
    }

    @ApiOperation(value = "提交领养宠物申请")
    @PostMapping("/applyAdopt")
    public HttpResult<Void> applyAdopt(@RequestBody @Validated AdoptApplyRequest request) {
        animalService.applyAdopt(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "提交捐助宠物申请")
    @PostMapping("/applyContribution")
    public HttpResult<Void> applyContribution(@RequestBody @Validated ContributionApplyRequest request) {
        animalService.applyContribution(request);
        return HttpResult.success();
    }

//    @ApiOperation(value = "重新提交领养宠物申请")
//    @PostMapping("/reApplyAdopt")
//    public HttpResult<Void> reApplyAdopt(@RequestBody @Validated AdoptApplyRequest request) {
//        animalService.applyAdopt(request);
//        return HttpResult.success();
//    }

    @ApiOperation(value = "发帖时获取宠物下拉列表")
    @GetMapping("/getAnimalListByCategoryId")
    public HttpResult<List<AnimalSelectListDTO>> getAnimalListByCategoryId(AnimalSelectRequest request) {
        List<AnimalSelectListDTO> animalSelectList = JavaBeanUtils.mapList(animalService.getAnimalListByCategoryId(request), AnimalSelectListDTO.class);
        return HttpResult.success(animalSelectList);
    }

}
