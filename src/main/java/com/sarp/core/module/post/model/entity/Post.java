package com.sarp.core.module.post.model.entity;

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
 * @date 2024/3/31 16:00
 * 帖子信息表
 *
*/

@ApiModel(description = "帖子信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_post")
public class Post extends BaseDO {

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 业务类型 1-领养 2-挂失 99-其他
     */
    @TableField(value = "biz_type")
    @ApiModelProperty(value = "业务类型 1-领养 2-挂失 99-其他")
    private Integer bizType;

    /**
     * 动物类目id
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "动物类目id")
    private String categoryId;

    /**
     * 摘要
     */
    @TableField(value = "post_abstract")
    @ApiModelProperty(value = "摘要")
    private String postAbstract;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "内容")
    private String content;


    /**
     * 状态 1-待审核 2-审核通过 3-审核拒绝 4-已关闭
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态 1-待审核 2-审核通过 3-审核拒绝 4-已关闭")
    private Integer status;

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

    /**
     * 关闭原因
     */
    @TableField(value = "close_reason")
    @ApiModelProperty(value = "关闭原因")
    private String closeReason;

}