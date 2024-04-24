package com.sarp.core.module.notice.model.entity;

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

/**
 * 公告类型信息表
 * @date 2024/4/20 18:52
 */

@ApiModel(description="公告类型信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_notice_type")
public class NoticeType extends BaseDO {

    /**
     * 公告类型名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="公告类型名称")
    private String name;

    /**
     * 简介
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value="简介")
    private String desc;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 状态 1-启用 2-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 1-启用 2-禁用")
    private Integer status;

}