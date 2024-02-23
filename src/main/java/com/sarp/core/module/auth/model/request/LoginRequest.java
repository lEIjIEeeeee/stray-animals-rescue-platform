package com.sarp.core.module.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/26 15:14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @ApiModelProperty(value = "登录账号或手机号")
    @NotBlank(message = "登录账号或手机号不能为空")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
