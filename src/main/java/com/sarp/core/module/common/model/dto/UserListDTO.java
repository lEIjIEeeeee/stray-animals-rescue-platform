package com.sarp.core.module.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/8 21:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListDTO {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "用户名称")
    private String name;

}
