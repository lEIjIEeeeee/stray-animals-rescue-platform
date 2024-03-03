package com.sarp.core.module.category.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/1 14:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDeleteRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

}
