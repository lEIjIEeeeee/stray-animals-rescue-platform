package com.sarp.core.module.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/2/28 11:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCloseRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "帖子关闭原因")
    @NotBlank(message = "帖子关闭原因不能为空")
    private String closeReason;

}
