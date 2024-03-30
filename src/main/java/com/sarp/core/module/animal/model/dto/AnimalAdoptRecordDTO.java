package com.sarp.core.module.animal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/3/29 11:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalAdoptRecordDTO {

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "领养人id")
    private String adoptUserId;

    @ApiModelProperty(value = "领养人名称")
    private String adoptUserName;

    @ApiModelProperty(value = "领养人登录帐号")
    private String adoptUserAccount;

    @ApiModelProperty(value = "领养人联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "领养开始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty(value = "领养结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty(value = "审核人id")
    private String auditId;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "审核人联系电话")
    private String auditorPhone;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
