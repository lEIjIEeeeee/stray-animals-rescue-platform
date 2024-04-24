package com.sarp.core.module.notice.controller;

import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.notice.model.dto.NoticeTypeDTO;
import com.sarp.core.module.notice.model.request.NoticeTypeDeleteRequest;
import com.sarp.core.module.notice.model.request.PlatformNoticeTypeQueryRequest;
import com.sarp.core.module.notice.model.request.NoticeTypeRequest;
import com.sarp.core.module.notice.model.response.PlatformNoticeTypeResponse;
import com.sarp.core.module.notice.service.NoticeTypeService;
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
 * @date 2024/4/20 18:15
 */

@Api(tags = "平台端公告模块-公告类型管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/noticeTypeModule/platform/noticeType")
public class PlatformNoticeTypeController {

    private NoticeTypeService noticeTypeService;

    @ApiOperation(value = "分页查询公告类型信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformNoticeTypeResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                           PlatformNoticeTypeQueryRequest request) {
        PageVO<PlatformNoticeTypeResponse> noticeTypeResponsePageVO =
                CommonConvert.convertPageToPageVo(noticeTypeService.listPage(request), PlatformNoticeTypeResponse.class);
        return HttpResult.success(noticeTypeResponsePageVO);
    }

    @ApiOperation(value = "查询公告类型详细信息")
    @GetMapping("/get")
    public HttpResult<PlatformNoticeTypeResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(noticeTypeService.get(id), PlatformNoticeTypeResponse.class));
    }

    @ApiOperation(value = "新增公告类型")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated(NoticeTypeRequest.Add.class)
                                        NoticeTypeRequest request) {
        noticeTypeService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改公告类型")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(NoticeTypeRequest.Edit.class)
                                         NoticeTypeRequest request) {
        noticeTypeService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除公告类型")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated NoticeTypeDeleteRequest request) {
        noticeTypeService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询公告类型下拉列表")
    @GetMapping("/dropdownList")
    public HttpResult<List<NoticeTypeDTO>> dropdownList() {
        return HttpResult.success(noticeTypeService.dropdownList());
    }

}
