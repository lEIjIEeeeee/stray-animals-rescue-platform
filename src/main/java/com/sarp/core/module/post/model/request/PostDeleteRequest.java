package com.sarp.core.module.post.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/2/28 11:29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDeleteRequest {

    @ApiModelProperty(value = "帖子id")
    @NotBlank(message = "id不能为空")
    private String id;

}
