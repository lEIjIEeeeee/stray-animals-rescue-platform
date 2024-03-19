package com.sarp.core.module.auth.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/18 22:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserIdRequest {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "id不能为空")
    private String id;

}
