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
 * @date 2024/4/8 10:31
 */

@ApiModel(description = "评论信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_comment")
public class Comment extends BaseDO {

    /**
     * 评论类型 ROOT-根评论 REPLAY-回复评论
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "评论类型 ROOT-根评论 REPLAY-回复评论")
    private String type;

    /**
     * 父级id
     */
    @TableField(value = "pid")
    @ApiModelProperty(value = "父级id")
    private String pid;

    /**
     * 根评论id
     */
    @TableField(value = "root_id")
    @ApiModelProperty(value = "根评论id")
    private String rootId;

    /**
     * 帖子id
     */
    @TableField(value = "post_id")
    @ApiModelProperty(value = "帖子id")
    private String postId;

    /**
     * 回复用户id
     */
    @TableField(value = "to_user_id")
    @ApiModelProperty(value = "回复用户id")
    private String toUserId;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "评论内容")
    private String content;

}