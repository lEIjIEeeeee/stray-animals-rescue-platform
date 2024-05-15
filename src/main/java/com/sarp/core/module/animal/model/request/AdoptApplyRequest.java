package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/29 10:55
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdoptApplyRequest {

    @ApiModelProperty(value = "宠物id")
    @NotBlank(message = "宠物id不能为空")
    private String animalId;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @ApiModelProperty(value = "领养备注")
    @NotBlank(message = "领养备注不能为空")
    private String remark;

}
