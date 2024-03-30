package com.sarp.core.module.animal.model.request;

import com.sarp.core.module.contribution.enums.ItemTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/30 18:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributionApplyRequest {

    @ApiModelProperty(value = "宠物id")
    @NotBlank(message = "宠物id不能为空")
    private String animalId;

    @ApiModelProperty(value = "捐助物类型")
    @NotNull(message = "类型不能为空")
    private ItemTypeEnum itemType;

    @ApiModelProperty(value = "捐助物名称")
    @NotBlank(message = "捐助物名称不能为空")
    private String itemName;

    @ApiModelProperty(value = "捐助物图片url")
    @NotBlank(message = "图片不能为空")
    private String itemPic;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @ApiModelProperty(value = "捐助备注")
    @NotBlank(message = "捐助备注不能为空")
    private String remark;

}
