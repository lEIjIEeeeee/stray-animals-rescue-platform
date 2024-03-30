package com.sarp.core.module.adopt.model.response;

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
 * @date 2024/3/29 16:54
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformAdoptResponse extends BaseResponse {

    @ApiModelProperty(value = "宠物id")
    private String animalId;

    @ApiModelProperty(value = "宠物名称")
    private String animalName;

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "领养人id")
    private String adoptUserId;

    @ApiModelProperty(value = "领养人名称")
    private String adoptUserName;

    @ApiModelProperty(value = "领养人联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "领养备注")
    private String remark;

    @ApiModelProperty(value = "状态 1-待审核 2-审核通过 3-审核拒绝")
    private Integer status;

    @ApiModelProperty(value = "审核人id")
    private String auditorId;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
