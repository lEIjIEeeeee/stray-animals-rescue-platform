package com.sarp.core.module.comment.model.entity;

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
 * 评论信息表
 * @date 2024/2/23 16:29
 */

@ApiModel(description="评论信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_comment")
public class Comment extends BaseDO {

    /**
     * 父级评论id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value="父级评论id")
    private String pid;

    /**
     * 帖子id
     */
    @TableField(value = "post_id")
    @ApiModelProperty(value="帖子id")
    private String postId;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="评论内容")
    private String content;

    /**
     * 是否显示 0-否 1-是
     */
    @TableField(value = "is_display")
    @ApiModelProperty(value="是否显示 0-否 1-是")
    private Integer isDisplay;

}