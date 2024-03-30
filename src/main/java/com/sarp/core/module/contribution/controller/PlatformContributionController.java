package com.sarp.core.module.contribution.controller;

import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.contribution.helper.ContributionHelper;
import com.sarp.core.module.contribution.model.dto.ContributionAuditDetailDTO;
import com.sarp.core.module.contribution.model.request.ContributionAuditRequest;
import com.sarp.core.module.contribution.model.request.PlatformContributionQueryRequest;
import com.sarp.core.module.contribution.model.response.PlatformContributionResponse;
import com.sarp.core.module.contribution.service.ContributionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/30 20:33
 */

@Api(tags = "平台端捐助模块-捐助信息管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/contributionModule/platform/contribution")
public class PlatformContributionController {

    private ContributionService contributionService;

    private ContributionHelper contributionHelper;

    @ApiOperation(value = "分页查询宠物捐助信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformContributionResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                             PlatformContributionQueryRequest request) {
        PageVO<PlatformContributionResponse> responsePageVO = CommonConvert
                .convertPageToPageVo(contributionService.platformListPage(request), PlatformContributionResponse.class);
        contributionHelper.fillPlatformContributionListData(responsePageVO.getDataList());
        return HttpResult.success(responsePageVO);
    }

    @ApiOperation(value = "审核时查询被捐助宠物、捐助人信息")
    @GetMapping("/getAuditDetail")
    public HttpResult<ContributionAuditDetailDTO> getAuditDetail(@RequestParam @NotBlank String id) {
        return HttpResult.success(contributionService.getAuditDetail(id));
    }

    @ApiOperation(value = "宠物捐助信息审核")
    @PostMapping("/audit")
    public HttpResult<Void> audit(@RequestBody @Validated ContributionAuditRequest request) {
        contributionService.audit(request);
        return HttpResult.success();
    }

}
