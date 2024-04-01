package com.sarp.core.module.common.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/3/9 11:47
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonalAnimalResponse extends BaseResponse {

    @ApiModelProperty(value = "宠物名称")
    private String name;

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "宠物照片url")
    private String picUrl;

    @ApiModelProperty(value = "遗失状态")
    private Integer isLost;

//    @ApiModelProperty(value = "捐助数")
//    private Integer contributeAmount;

}
