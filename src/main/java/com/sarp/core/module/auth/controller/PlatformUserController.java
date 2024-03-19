package com.sarp.core.module.auth.controller;

import com.sarp.core.module.auth.helper.UserHelper;
import com.sarp.core.module.auth.model.dto.UserDetailDTO;
import com.sarp.core.module.auth.model.request.ChangeStatusRequest;
import com.sarp.core.module.auth.model.request.UserIdRequest;
import com.sarp.core.module.auth.model.request.UserQueryRequest;
import com.sarp.core.module.auth.model.request.UserRequest;
import com.sarp.core.module.auth.model.response.UserResponse;
import com.sarp.core.module.auth.service.UserService;
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
 * @date 2024/3/18 10:42
 */

@Api(tags = "权限模块-用户接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/platform/user")
public class PlatformUserController {

    private UserService userService;

    private UserHelper userHelper;

    @ApiOperation(value = "分页查询用户信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<UserResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             UserQueryRequest request) {
        PageVO<UserResponse> userResponsePageVO = CommonConvert.convertPageToPageVo(userService.listPage(request), UserResponse.class);
        userHelper.fillUserListData(userResponsePageVO.getDataList());
        return HttpResult.success(userResponsePageVO);
    }

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/get")
    public HttpResult<UserDetailDTO> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(userService.get(id));
    }

    @ApiOperation(value = "新增用户信息")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated(UserRequest.Add.class)
                                        UserRequest request) {
        userService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(UserRequest.Edit.class)
                                         UserRequest request) {
        userService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "更改用户状态")
    @PostMapping("/changeStatus")
    public HttpResult<Void> changeStatus(@RequestBody @Validated ChangeStatusRequest request) {
        userService.changeStatus(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    public HttpResult<Void> resetPassword(@RequestBody @Validated UserIdRequest request) {
        userService.resetPassword(request);
        return HttpResult.success();
    }

    //TODO 用户删除，如何处理该用户下已经存在的各项业务数据

}
