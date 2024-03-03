package com.sarp.core.module.category.model.request;

import com.sarp.core.module.common.enums.EnableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/1 10:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryChangeStatusRequest {

    @ApiModelProperty(value = "id不能为空")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "更改状态")
    @NotNull(message = "状态不能为空")
    private EnableStatusEnum status;

}
