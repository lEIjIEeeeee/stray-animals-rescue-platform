package com.sarp.core.module.comment.model.response;

import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/19 11:20
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformCommentResponse extends BaseResponse {

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论用户id")
    private String createId;

    @ApiModelProperty(value = "评论用户名称")
    private String createName;

    @ApiModelProperty(value = "评论类型")
    private String type;

    @ApiModelProperty(value = "回复用户id")
    private String toUserId;

    @ApiModelProperty(value = "回复用户名称")
    private String toUserName;

    @ApiModelProperty(value = "帖子id")
    private String postId;

    @ApiModelProperty(value = "帖子标题")
    private String title;

}
