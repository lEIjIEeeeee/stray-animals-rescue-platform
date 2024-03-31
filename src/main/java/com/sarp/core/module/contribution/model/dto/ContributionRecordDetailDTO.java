package com.sarp.core.module.contribution.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/3/31 10:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributionRecordDetailDTO {

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "宠物名称")
    private String animalName;

    @ApiModelProperty(value = "宠物编号")
    private String animalNo;

    @ApiModelProperty(value = "动物类目")
    private String categoryName;

    @ApiModelProperty(value = "宠物主人名称")
    private String ownerName;

    @ApiModelProperty(value = "宠物主人手机号")
    private String ownerPhone;

    @ApiModelProperty(value = "宠物图片")
    private String picUrl;

    @ApiModelProperty(value = "捐助人名称")
    private String applyUserName;

    @ApiModelProperty(value = "捐助人登录账号")
    private String applyUserAccount;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "捐助类型")
    private Integer itemType;

    @ApiModelProperty(value = "捐助物名称")
    private String itemName;

    @ApiModelProperty(value = "捐助物图片")
    private String itemPic;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "捐助备注")
    private String remark;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

    @ApiModelProperty(value = "审核人名称")
    private String auditUserName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
