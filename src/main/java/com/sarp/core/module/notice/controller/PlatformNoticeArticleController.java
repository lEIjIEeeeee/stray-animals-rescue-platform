package com.sarp.core.module.notice.controller;

import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.notice.helper.NoticeArticleHelper;
import com.sarp.core.module.notice.model.dto.NoticeArticleDetailDTO;
import com.sarp.core.module.notice.model.request.NoticeArticleDeleteRequest;
import com.sarp.core.module.notice.model.request.NoticeArticleRequest;
import com.sarp.core.module.notice.model.request.PlatformNoticeArticleQueryRequest;
import com.sarp.core.module.notice.model.response.PlatformNoticeArticleResponse;
import com.sarp.core.module.notice.service.NoticeArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/20 18:15
 */

@Api(tags = "平台端公告模块-公告文章管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/noticeModule/platform/noticeArticle")
public class PlatformNoticeArticleController {

    private NoticeArticleService noticeArticleService;

    private NoticeArticleHelper noticeArticleHelper;

    @ApiOperation(value = "分页查询公告文章信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformNoticeArticleResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                              PlatformNoticeArticleQueryRequest request) {
        PageVO<PlatformNoticeArticleResponse> noticeArticleResponsePageVO =
                CommonConvert.convertPageToPageVo(noticeArticleService.listPage(request), PlatformNoticeArticleResponse.class);
        noticeArticleHelper.fillNoticeArticleListData(noticeArticleResponsePageVO.getDataList());
        return HttpResult.success(noticeArticleResponsePageVO);
    }

    @ApiOperation(value = "查询公告文章详细信息")
    @GetMapping("/get")
    public HttpResult<NoticeArticleDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(noticeArticleService.get(id));
    }

    @ApiOperation(value = "新增公告文章")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated(NoticeArticleRequest.Add.class)
                                        NoticeArticleRequest request) {
        noticeArticleService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑公告文章")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(NoticeArticleRequest.Edit.class)
                                         NoticeArticleRequest request) {
        noticeArticleService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除公告文章")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated NoticeArticleDeleteRequest request) {
        noticeArticleService.delete(request);
        return HttpResult.success();
    }

}
