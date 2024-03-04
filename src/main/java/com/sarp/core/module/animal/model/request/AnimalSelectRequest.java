package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/4 0:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalSelectRequest {

    @ApiModelProperty(value = "动物类目id")
    private String categoryId;

}
