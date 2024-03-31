package com.sarp.core.module.animal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/3/31 11:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalContributionRecordDTO {

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "捐助人id")
    private String applyUserId;

    @ApiModelProperty(value = "捐助人名称")
    private String applyUserName;

    @ApiModelProperty(value = "捐助人账号")
    private String applyUserAccount;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "捐助类型")
    private Integer itemType;

    @ApiModelProperty(value = "捐助物名称")
    private String itemName;

    @ApiModelProperty(value = "捐助物图片")
    private String itemPic;

    @ApiModelProperty(value = "捐助备注")
    private String remark;

    @ApiModelProperty(value = "审核人id")
    private String auditId;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "审核人联系电话")
    private String auditorPhone;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
