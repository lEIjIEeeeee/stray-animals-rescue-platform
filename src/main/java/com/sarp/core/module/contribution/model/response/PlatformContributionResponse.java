package com.sarp.core.module.contribution.model.response;

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
 * @date 2024/3/30 20:40
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformContributionResponse extends BaseResponse {

    @ApiModelProperty(value = "宠物id")
    private String animalId;

    @ApiModelProperty(value = "宠物名称")
    private String animalName;

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "捐助物类型")
    private Integer itemType;

    @ApiModelProperty(value = "捐助物名称")
    private String itemName;

    @ApiModelProperty(value = "捐助物图片")
    private String itemPic;

    @ApiModelProperty(value = "捐助人id")
    private String createId;

    @ApiModelProperty(value = "捐助人名称")
    private String applyUserName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "捐助备注")
    private String remark;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

    @ApiModelProperty(value = "审核人id")
    private String auditId;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
