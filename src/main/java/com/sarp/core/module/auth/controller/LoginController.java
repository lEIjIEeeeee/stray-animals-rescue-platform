package com.sarp.core.module.auth.controller;

import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.auth.model.dto.RegisterReturnDTO;
import com.sarp.core.module.auth.model.dto.SysTokenLoginDTO;
import com.sarp.core.module.auth.model.request.LoginRequest;
import com.sarp.core.module.auth.model.request.RegisterRequest;
import com.sarp.core.module.auth.service.LoginService;
import com.sarp.core.module.common.model.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @date 2024/1/23 9:26
 */

@Api(tags = "权限模块-用户登录注册相关接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/login")
public class LoginController {

    private LoginService loginService;

    @ApiOperation(value = "用户注册接口")
    @PostMapping("/register")
    public HttpResult<RegisterReturnDTO> register(@RequestBody @Validated RegisterRequest request) {
        return HttpResult.success(loginService.register(request));
    }

    @ApiOperation(value = "用户登录接口")
    @PostMapping("/login")
    public HttpResult<LoginUser> login(@RequestBody @Validated LoginRequest request) {
        return HttpResult.success(loginService.login(request));
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public HttpResult<Void> logout() {
        loginService.logout();
        return HttpResult.success();
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/sysTokenLogin")
    public HttpResult<SysTokenLoginDTO> sysTokenLogin() {
        return HttpResult.success(loginService.sysTokenLogin());
    }

}
