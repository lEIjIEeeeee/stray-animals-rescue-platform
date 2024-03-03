package com.sarp.core.module.animal.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/4 0:05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalSelectListDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "动物名称")
    private String name;

}