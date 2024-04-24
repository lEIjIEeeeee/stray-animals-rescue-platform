package com.sarp.core.module.comment.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/8 11:44
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplayRequest {

    @ApiModelProperty(value = "评论id")
    @NotBlank(message = "评论id不能为空")
    private String id;

    @ApiModelProperty(value = "评论回复内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
