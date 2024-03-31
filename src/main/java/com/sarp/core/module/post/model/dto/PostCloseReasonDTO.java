package com.sarp.core.module.post.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/2/28 23:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCloseReasonDTO {

    @ApiModelProperty(value = "帖子关闭原因")
    private String closeReason;

}
