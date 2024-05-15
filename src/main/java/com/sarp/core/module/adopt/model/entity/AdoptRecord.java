package com.sarp.core.module.adopt.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sarp.core.module.common.model.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/3/29 11:32
 * 流浪动物领养申请记录信息表
 *
*/

@ApiModel(description = "流浪动物领养申请记录信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_adopt_record")
public class AdoptRecord extends BaseDO {

    /**
     * 宠物id
     */
    @TableField(value = "animal_id")
    @ApiModelProperty(value = "宠物id")
    private String animalId;

    /**
     * 审核状态 1-待审核 2-审核通过 3-审核拒绝
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "审核状态 1-待审核 2-审核通过 3-审核拒绝")
    private Integer status;

    /**
     * 是否重新提交申请 0-否 1-是
     */
    @TableField(value = "reapply_flag")
    @ApiModelProperty(value = "是否重新提交申请 0-否 1-是")
    private Integer reapplyFlag;

    /**
     * 联系电话
     */
    @TableField(value = "contact_phone")
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    /**
     * 领养备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "领养备注")
    private String remark;

    /**
     * 领养开始日期
     */
    @TableField(value = "start_date")
    @ApiModelProperty(value = "领养开始日期")
    private Date startDate;

    /**
     * 领养结束日期
     */
    @TableField(value = "end_date")
    @ApiModelProperty(value = "领养结束日期")
    private Date endDate;

    /**
     * 审核人id
     */
    @TableField(value = "audit_id")
    @ApiModelProperty(value = "审核人id")
    private String auditId;

    /**
     * 审核时间
     */
    @TableField(value = "audit_time")
    @ApiModelProperty(value = "审核时间")
    private Date auditTime;

    /**
     * 审核备注
     */
    @TableField(value = "audit_remark")
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}