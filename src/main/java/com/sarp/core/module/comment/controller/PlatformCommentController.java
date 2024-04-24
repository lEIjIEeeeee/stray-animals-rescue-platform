package com.sarp.core.module.comment.controller;

import com.sarp.core.module.comment.helper.CommentHelper;
import com.sarp.core.module.comment.model.request.CommentDeleteRequest;
import com.sarp.core.module.comment.model.request.PlatformCommentQueryRequest;
import com.sarp.core.module.comment.model.response.PlatformCommentResponse;
import com.sarp.core.module.comment.service.CommentService;
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

/**
 * @date 2024/4/19 11:17
 */

@Api(tags = "平台端评论模块-评论管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commentModule/platform/comment")
public class PlatformCommentController {

    private CommentService commentService;

    private CommentHelper commentHelper;

    @ApiOperation(value = "分页查询评论信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformCommentResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                        PlatformCommentQueryRequest request) {
        PageVO<PlatformCommentResponse> commentPageVO =
                CommonConvert.convertPageToPageVo(commentService.platformListPage(request), PlatformCommentResponse.class);
        commentHelper.fillPlatformCommentListData(commentPageVO.getDataList());
        return HttpResult.success(commentPageVO);
    }

    @ApiOperation(value = "删除评论")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated CommentDeleteRequest request) {
        commentService.platformDelete(request);
        return HttpResult.success();
    }

}
