package com.sarp.core.module.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/4 16:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysTokenLoginDTO {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "昵称")
    private String nickName;

}
