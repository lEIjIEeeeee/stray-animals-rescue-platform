package com.sarp.core.module.comment.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/4/9 12:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplayCountDTO {

    @ApiModelProperty(value = "根评论id")
    private String rootId;

    @ApiModelProperty(value = "回复评论数")
    private Long replayCount;

}
