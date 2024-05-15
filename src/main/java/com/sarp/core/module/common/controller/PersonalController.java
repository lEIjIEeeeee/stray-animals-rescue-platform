package com.sarp.core.module.common.controller;

import com.sarp.core.module.common.model.request.PersonalInfoEditRequest;
import com.sarp.core.module.common.model.request.PersonalListQueryRequest;
import com.sarp.core.module.common.helper.CommonHelper;
import com.sarp.core.module.common.model.HttpResult;
import com.sarp.core.module.common.model.convert.CommonConvert;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.common.model.response.AdoptRecordResponse;
import com.sarp.core.module.common.model.response.PersonalAnimalResponse;
import com.sarp.core.module.common.model.response.PersonalPostResponse;
import com.sarp.core.module.common.model.vo.PageVO;
import com.sarp.core.module.common.service.CommonService;
import com.sarp.core.module.common.service.PersonalService;
import com.sarp.core.module.post.helper.PostHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2024/3/12 10:33
 */

@Api(tags = "公共模块-用户个人信息相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commonModule/personal")
public class PersonalController {

    private CommonService commonService;
    private PersonalService personalService;

    private CommonHelper commonHelper;
    private PostHelper postHelper;

    @ApiOperation(value = "分页查询我的宠物信息列表")
    @GetMapping("/listPageAnimal")
    public HttpResult<PageVO<PersonalAnimalResponse>> listPageAnimal(@Validated(BaseQueryRequest.ListPage.class)
                                                                             PersonalListQueryRequest request) {
        PageVO<PersonalAnimalResponse> animalResponsePageVO =
                CommonConvert.convertPageToPageVo(commonService.listPageAnimal(request), PersonalAnimalResponse.class);
        commonHelper.fillPersonalAnimalListData(animalResponsePageVO.getDataList());
        return HttpResult.success(animalResponsePageVO);
    }

    @ApiOperation(value = "分页查询我的帖子信息列表")
    @GetMapping("/listPagePost")
    public HttpResult<PageVO<PersonalPostResponse>> listPagePost(@Validated(BaseQueryRequest.ListPage.class)
                                                                         PersonalListQueryRequest request) {
        PageVO<PersonalPostResponse> postResponsePageVO =
                CommonConvert.convertPageToPageVo(commonService.listPagePost(request), PersonalPostResponse.class);
        postHelper.fillPostListData(postResponsePageVO.getDataList());
        return HttpResult.success(postResponsePageVO);
    }

    @ApiOperation(value = "分页查询我的领养记录信息列表")
    @GetMapping("/listPageAdoptRecord")
    public HttpResult<PageVO<AdoptRecordResponse>> listPageAdoptRecord(@Validated(BaseQueryRequest.ListPage.class)
                                                                               PersonalListQueryRequest request) {
        PageVO<AdoptRecordResponse> adoptRecordResponsePageVO =
                CommonConvert.convertPageToPageVo(commonService.listPageAdoptRecord(request), AdoptRecordResponse.class);
        commonHelper.fillAdoptRecordListData(adoptRecordResponsePageVO.getDataList());
        return HttpResult.success(adoptRecordResponsePageVO);
    }

    @ApiOperation(value = "修改个人用户信息")
    @PostMapping("/editPersonalInfo")
    public HttpResult<Void> editPersonalInfo(@RequestBody @Validated PersonalInfoEditRequest request) {
        personalService.editPersonalInfo(request);
        return HttpResult.success();
    }

}
