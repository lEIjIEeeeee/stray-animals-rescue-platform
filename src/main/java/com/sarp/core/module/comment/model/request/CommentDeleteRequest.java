package com.sarp.core.module.comment.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/20 16:45
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDeleteRequest {

    @ApiModelProperty(value = "评论id")
    @NotBlank(message = "评论id不能为空")
    private String id;

}
