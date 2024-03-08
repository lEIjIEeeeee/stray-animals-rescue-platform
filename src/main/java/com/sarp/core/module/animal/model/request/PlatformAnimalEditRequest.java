package com.sarp.core.module.animal.model.request;

import com.sarp.core.module.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/7 9:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatformAnimalEditRequest {

    @ApiModelProperty(value = "宠物id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "宠物名称")
    private String name;

    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    @ApiModelProperty(value = "动物类目id")
    @NotBlank(message = "动物类目id不能为空")
    private String categoryId;

    @ApiModelProperty(value = "当前主人id")
    private String ownerId;

    @ApiModelProperty(value = "描述信息")
    private String desc;

}
