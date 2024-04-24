package com.sarp.core.module.notice.controller;

import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.notice.model.dto.NoticeArticleDetailDTO;
import com.sarp.core.module.notice.model.dto.NoticeTypeDTO;
import com.sarp.core.module.notice.model.request.NoticeQueryRequest;
import com.sarp.core.module.notice.model.response.NoticeResponse;
import com.sarp.core.module.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @date 2024/4/24 0:57
 */

@Api(tags = "用户端公告模块-公告须知相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/noticeModule/notice")
public class NoticeController {

    private NoticeService noticeService;

    @ApiOperation(value = "分页查询公告列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<NoticeResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                               NoticeQueryRequest request) {
        PageVO<NoticeResponse> noticeResponsePageVO = CommonConvert.convertPageToPageVo(noticeService.listPage(request), NoticeResponse.class);
        return HttpResult.success(noticeResponsePageVO);
    }

    @ApiOperation(value = "查询公告详情")
    @GetMapping("/get")
    public HttpResult<NoticeArticleDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(noticeService.get(id));
    }

    @ApiOperation(value = "查询公告类型下拉列表")
    @GetMapping("/getNoticeTypeDropdownList")
    public HttpResult<List<NoticeTypeDTO>> getNoticeTypeDropdownList() {
        return HttpResult.success(noticeService.getNoticeTypeDropdownList());
    }

}
