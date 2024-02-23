package com.sarp.core.module.post.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.post.helper.PostHelper;
import com.sarp.core.module.post.model.entity.Post;
import com.sarp.core.module.post.model.request.PlatformPostQueryRequest;
import com.sarp.core.module.post.model.response.PlatformPostResponse;
import com.sarp.core.module.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2024/1/30 11:51
 */

@Api(tags = "帖子模块-后台帖子信息管理相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/postModule/platform/post")
public class PlatformPostController {

    private PostService postService;

    private PostHelper postHelper;

    @ApiOperation(value = "平台管理员分页查询帖子列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<PlatformPostResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                     PlatformPostQueryRequest request) {
        Page<Post> postPage = postService.listPagePlatform(request);
        PageVO<PlatformPostResponse> postResponsePageVO = CommonConvert.convertPageToPageVo(postPage, PlatformPostResponse.class);
        postHelper.fillPostAbstractData(postPage, postResponsePageVO);
        postHelper.fillPostListData(postResponsePageVO.getDataList());
        return HttpResult.success(postResponsePageVO);
    }

}
