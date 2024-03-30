package com.sarp.core.module.animal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @date 2024/3/28 23:57
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

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "体重")
    private BigDecimal weight;

    @ApiModelProperty(value = "宠物主人id")
    private String ownerId;

    @ApiModelProperty(value = "宠物主人名称")
    private String ownerName;

    @ApiModelProperty(value = "是否领养")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失")
    private Integer isLost;

    @ApiModelProperty(value = "宠物图片")
    private String picUrl;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "领养记录")
    private List<AnimalAdoptRecordDTO> adoptRecordList;

}
