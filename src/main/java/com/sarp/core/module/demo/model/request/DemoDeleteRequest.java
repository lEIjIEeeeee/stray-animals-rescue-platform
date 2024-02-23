package com.sarp.core.module.demo.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/22 16:03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemoDeleteRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

}
