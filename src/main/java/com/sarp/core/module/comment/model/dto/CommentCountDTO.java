package com.sarp.core.module.comment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/4/15 16:36
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCountDTO {

    @ApiModelProperty(value = "评论总数（包括回复评论）")
    private Long count;

}
