package com.sarp.core.module.post.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.post.helper.PostHelper;
import com.sarp.core.module.post.model.dto.PlatformPostDetailDTO;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.post.model.request.PlatformPostQueryRequest;
import com.sarp.core.module.post.model.request.PostAuditRequest;
import com.sarp.core.module.post.model.request.PostCloseRequest;
import com.sarp.core.module.post.model.request.PostDeleteRequest;
import com.sarp.core.module.post.model.response.PlatformPostResponse;
import com.sarp.core.module.post.model.dto.PostCloseReasonDTO;
import com.sarp.core.module.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/30 11:51
 */

@Api(tags = "平台端帖子模块-帖子信息管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/postModule/platform/post")
public class PlatformPostController {

    private PostService postService;

    private PostHelper postHelper;

    @ApiOperation(value = "分页查询帖子列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformPostResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                     PlatformPostQueryRequest request) {
        Page<Post> postPage = postService.listPagePlatform(request);
        PageVO<PlatformPostResponse> postResponsePageVO = CommonConvert.convertPageToPageVo(postPage, PlatformPostResponse.class);
        postHelper.fillPostListData(postResponsePageVO.getDataList());
        postHelper.fillPlatformListAuditorData(postResponsePageVO.getDataList());
        return HttpResult.success(postResponsePageVO);
    }

    @ApiOperation(value = "查询帖子详情")
    @GetMapping("/get")
    public HttpResult<PlatformPostDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(postService.getPlatform(id));
    }

    @ApiOperation(value = "审核帖子")
    @PostMapping("/audit")
    public HttpResult<Void> audit(@RequestBody @Validated PostAuditRequest request) {
        postService.audit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "关闭帖子")
    @PostMapping("/close")
    public HttpResult<Void> close(@RequestBody @Validated PostCloseRequest request) {
        postService.close(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查看帖子关闭原因")
    @GetMapping("/getCloseReason")
    public HttpResult<PostCloseReasonDTO> getCloseReason(@RequestParam @NotBlank String id) {
        return HttpResult.success(postService.getCloseReason(id));
    }

    @ApiOperation(value = "删除帖子")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated PostDeleteRequest request) {
        postService.platformDelete(request);
        return HttpResult.success();
    }

}
