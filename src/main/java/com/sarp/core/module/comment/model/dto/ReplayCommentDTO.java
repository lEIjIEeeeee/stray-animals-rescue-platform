package com.sarp.core.module.comment.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/4/8 15:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplayCommentDTO {

    @ApiModelProperty(value = "回复评论id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "根评论id")
    private String rootId;

    @ApiModelProperty(value = "回复评论内容")
    private String content;

    @ApiModelProperty(value = "评论用户id")
    private String createId;

    @ApiModelProperty(value = "评论用户昵称")
    private String nickName;

    @ApiModelProperty(value = "评论用户头像")
    private String avatar;

    @ApiModelProperty(value = "回复目标用户id")
    private String toUserId;

    @ApiModelProperty(value = "回复目标用户昵称")
    private String toUserName;

    @ApiModelProperty(value = "评论时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
