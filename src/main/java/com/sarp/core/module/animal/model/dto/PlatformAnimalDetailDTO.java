package com.sarp.core.module.animal.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/7 17:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatformAnimalDetailDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "宠物名称")
    private String name;

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "动物类目id")
    private String categoryId;

    @ApiModelProperty(value = "动物类目名称")
    private String categoryName;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "宠物主人id")
    private String ownerId;

    @ApiModelProperty(value = "宠物主人名称")
    private String ownerName;

    @ApiModelProperty(value = "是否领养")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失")
    private Integer isLost;

    @ApiModelProperty(value = "描述信息")
    private String desc;

}
