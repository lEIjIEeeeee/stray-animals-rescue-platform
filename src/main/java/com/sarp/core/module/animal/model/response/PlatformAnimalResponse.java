package com.sarp.core.module.animal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/3/7 0:16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformAnimalResponse extends AnimalResponse {

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "当前主人id")
    private String ownerId;

    @ApiModelProperty(value = "当前主人名称")
    private String ownerName;

    @ApiModelProperty(value = "是否领养 0-否 1-是")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失 0-否 1-是")
    private Integer isLost;

}
