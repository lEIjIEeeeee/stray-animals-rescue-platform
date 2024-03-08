package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/7 9:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalDeleteRequest {

    @ApiModelProperty(value = "宠物id")
    @NotBlank(message = "id不能为空")
    private String id;

}
