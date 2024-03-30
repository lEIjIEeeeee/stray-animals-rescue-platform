package com.sarp.core.module.adopt.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/3/30 15:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptRecordDetailDTO {

    @ApiModelProperty(value = "记录id")
    private String id;

    @ApiModelProperty(value = "审核状态")
    private Integer status;

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

    @ApiModelProperty(value = "图片")
    private String picUrl;

    @ApiModelProperty(value = "领养人名称")
    private String adoptUserName;

    @ApiModelProperty(value = "领养人账号")
    private String adoptUserAccount;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "领养备注")
    private String remark;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    @ApiModelProperty(value = "审核结果")
    private String auditResult;

    @ApiModelProperty(value = "审核人名称")
    private String auditUserName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
