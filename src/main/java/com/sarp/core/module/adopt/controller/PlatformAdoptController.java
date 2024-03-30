package com.sarp.core.module.adopt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.adopt.helper.AdoptHelper;
import com.sarp.core.module.adopt.model.dto.AdoptAuditDetailDTO;
import com.sarp.core.module.adopt.model.dto.AdoptRecordDetailDTO;
import com.sarp.core.module.adopt.model.entity.AdoptRecord;
import com.sarp.core.module.adopt.model.request.AdoptAuditRequest;
import com.sarp.core.module.adopt.model.request.PlatformAdoptQueryRequest;
import com.sarp.core.module.adopt.model.response.PlatformAdoptResponse;
import com.sarp.core.module.adopt.service.AdoptService;
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
 * @date 2024/3/29 16:51
 */

@Api(tags = "平台端领养模块-领养信息管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/adoptModule/platform/adopt")
public class PlatformAdoptController {

    private AdoptService adoptService;

    private AdoptHelper adoptHelper;

    @ApiOperation(value = "分页查询领养记录信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformAdoptResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                      PlatformAdoptQueryRequest request) {
        Page<AdoptRecord> adoptRecordPage = adoptService.platformListPage(request);
        PageVO<PlatformAdoptResponse> responsePageVO = CommonConvert.convertPageToPageVo(adoptRecordPage, PlatformAdoptResponse.class);
        adoptHelper.fillPlatformAdoptListData(adoptRecordPage.getRecords(), responsePageVO.getDataList());
        return HttpResult.success(responsePageVO);
    }

    @ApiOperation(value = "查询领养记录审核详情")
    @GetMapping("/getRecordDetail")
    public HttpResult<AdoptRecordDetailDTO> getRecordDetail(@RequestParam @NotBlank String id) {
        return HttpResult.success(adoptService.getRecordDetail(id));
    }

    @ApiOperation(value = "审核时查询被领养宠物、申请人信息")
    @GetMapping("/getAuditDetail")
    public HttpResult<AdoptAuditDetailDTO> getAuditDetail(@RequestParam @NotBlank String id) {
        return HttpResult.success(adoptService.getAuditDetail(id));
    }

    @ApiOperation(value = "宠物领养信息审核")
    @PostMapping("/audit")
    public HttpResult<Void> audit(@RequestBody @Validated AdoptAuditRequest request) {
        adoptService.audit(request);
        return HttpResult.success();
    }

}
