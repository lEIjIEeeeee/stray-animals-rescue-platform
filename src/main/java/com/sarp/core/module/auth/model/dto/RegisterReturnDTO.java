package com.sarp.core.module.auth.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/15 11:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterReturnDTO {

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(name = "用户账号")
    private String account;

}
