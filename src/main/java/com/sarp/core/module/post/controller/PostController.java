package com.sarp.core.module.post.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.exception.BizException;
import com.sarp.core.module.common.enums.HttpResultCode;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.post.helper.PostHelper;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.post.model.request.PostQueryRequest;
import com.sarp.core.module.post.model.request.SubmitPostRequest;
import com.sarp.core.module.post.model.response.PostResponse;
import com.sarp.core.module.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/30 11:18
 */

@Api(tags = "帖子模块-用户发帖相关接口")
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/postModule/post")
public class PostController {

    private PostService postService;

    private PostHelper postHelper;

    @ApiOperation(value = "发起新帖子")
    @PostMapping("/submitPost")
    public HttpResult<Void> submitPost(@RequestBody @Validated(SubmitPostRequest.Add.class)
                                               SubmitPostRequest request) {
        postService.submitPost(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "重新发起审核退回帖子")
    @PostMapping("/resubmitPost")
    public HttpResult<Void> resubmitPost(@RequestBody @Validated(SubmitPostRequest.Edit.class)
                                                 SubmitPostRequest request) {
        postService.resubmitPost(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "分页查询帖子列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PostResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                     PostQueryRequest request) {
        Page<Post> postPage = postService.listPage(request);
        PageVO<PostResponse> postResponsePageVO = CommonConvert.convertPageToPageVo(postPage, PostResponse.class);
//        postHelper.fillPostAbstractData(postPage, postResponsePageVO);
        postHelper.fillPostListData(postResponsePageVO.getDataList());
        return HttpResult.success(postResponsePageVO);
    }

    @ApiOperation(value = "查询详情")
    @GetMapping("/get")
    public HttpResult<Void> get(@RequestParam @NotBlank String id) {
        System.out.println(1);
        return HttpResult.success();
    }

}
