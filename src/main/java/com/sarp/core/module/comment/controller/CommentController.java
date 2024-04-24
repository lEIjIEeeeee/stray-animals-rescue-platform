package com.sarp.core.module.comment.controller;

import com.sarp.core.module.comment.model.dto.CommentCountDTO;
import com.sarp.core.module.comment.model.dto.ReplayCommentDTO;
import com.sarp.core.module.comment.model.request.*;
import com.sarp.core.module.comment.model.response.CommentResponse;
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

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/2/23 16:30
 */

@Api(tags = "用户端评论模块-用户评论相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commentModule/comment")
public class CommentController {

    private CommentService commentService;

    @ApiOperation(value = "分页查询评论列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<CommentResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                CommentQueryRequest request) {
        return HttpResult.success(commentService.listPage(request));
    }

    @ApiOperation(value = "分页查询评论下回复评论列表")
    @GetMapping("/listPageReplay")
    public HttpResult<PageVO<ReplayCommentDTO>> listPageReplay(@Validated(BaseQueryRequest.ListPage.class)
                                                                       ReplayCommentQueryRequest request) {
        PageVO<ReplayCommentDTO> replayCommentPageVO = CommonConvert.convertPageToPageVo(commentService.listPageReplay(request), ReplayCommentDTO.class);
        commentService.fillReplayListData(replayCommentPageVO.getDataList());
        return HttpResult.success(replayCommentPageVO);
    }

    @ApiOperation(value = "用户发送评论")
    @PostMapping("/sendComment")
    public HttpResult<Void> sendComment(@RequestBody @Validated SendCommentRequest request) {
        commentService.sendComment(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "用户回复评论")
    @PostMapping("/replay")
    public HttpResult<ReplayCommentDTO> replay(@RequestBody @Validated ReplayRequest request) {
        return HttpResult.success(commentService.replay(request));
    }

    @ApiOperation(value = "查询获取评论数（评论+回复总数）")
    @GetMapping("/getCounts")
    public HttpResult<CommentCountDTO> getCounts(@RequestParam @NotBlank String postId) {
        return HttpResult.success(commentService.getCounts(postId));
    }

    @ApiOperation(value = "用户删除评论")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated CommentDeleteRequest request) {
        commentService.delete(request);
        return HttpResult.success();
    }

}
