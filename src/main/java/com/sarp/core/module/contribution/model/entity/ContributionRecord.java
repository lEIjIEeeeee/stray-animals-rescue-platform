package com.sarp.core.module.contribution.model.entity;

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
 * @date 2024/3/30 17:56
 * 流浪动物捐助申请记录信息表
 *
*/

@ApiModel(description="流浪动物捐助申请记录信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_contribution_record")
public class ContributionRecord extends BaseDO {

    /**
     * 宠物id
     */
    @TableField(value = "animal_id")
    @ApiModelProperty(value="宠物id")
    private String animalId;

    /**
     * 审核状态 1-待审核 2-审核通过 3-审核拒绝
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="审核状态 1-待审核 2-审核通过 3-审核拒绝")
    private Integer status;

    /**
     * 捐助物类型 1-食物 2-衣物 3-玩具 99-其他
     */
    @TableField(value = "item_type")
    @ApiModelProperty(value="捐助物类型 1-食物 2-衣物 3-玩具 99-其他")
    private Integer itemType;

    /**
     * 捐助物名称
     */
    @TableField(value = "item_name")
    @ApiModelProperty(value="捐助物名称")
    private String itemName;

    /**
     * 捐助物图片url
     */
    @TableField(value = "item_pic")
    @ApiModelProperty(value="捐助物图片url")
    private String itemPic;

    /**
     * 联系电话
     */
    @TableField(value = "contact_phone")
    @ApiModelProperty(value="联系电话")
    private String contactPhone;

    /**
     * 捐助备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="捐助备注")
    private String remark;

    /**
     * 审核人id
     */
    @TableField(value = "audit_id")
    @ApiModelProperty(value="审核人id")
    private String auditId;

    /**
     * 审核时间
     */
    @TableField(value = "audit_time")
    @ApiModelProperty(value="审核时间")
    private Date auditTime;

    /**
     * 审核备注
     */
    @TableField(value = "audit_remark")
    @ApiModelProperty(value="审核备注")
    private String auditRemark;

}