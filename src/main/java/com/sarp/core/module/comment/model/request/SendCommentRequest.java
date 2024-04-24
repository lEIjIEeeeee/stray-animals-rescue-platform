package com.sarp.core.module.comment.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/8 11:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendCommentRequest {

    @ApiModelProperty(value = "帖子id")
    @NotBlank(message = "帖子id不能为空")
    private String id;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
