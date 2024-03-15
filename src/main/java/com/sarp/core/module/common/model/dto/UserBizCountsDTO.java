package com.sarp.core.module.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/10 16:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBizCountsDTO {

    @ApiModelProperty(value = "总访问量")
    private Integer accessAmount;

    @ApiModelProperty(value = "宠物总数")
    private Integer animalAmount;

    @ApiModelProperty(value = "帖子总数")
    private Integer postAmount;

    @ApiModelProperty(value = "申请总数")
    private Integer applyAmount;

}
