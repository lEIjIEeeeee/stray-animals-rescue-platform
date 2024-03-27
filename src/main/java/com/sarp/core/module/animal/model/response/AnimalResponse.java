package com.sarp.core.module.animal.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/3/25 15:35
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AnimalResponse extends BaseResponse {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "动物类目id")
    private String categoryId;

    @ApiModelProperty(value = "动物类目名称")
    private String categoryName;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "是否领养")
    private Integer isAdopt;

    @ApiModelProperty("是否遗失")
    private Integer isLost;

    @ApiModelProperty(value = "宠物图片")
    private String picUrl;


}
