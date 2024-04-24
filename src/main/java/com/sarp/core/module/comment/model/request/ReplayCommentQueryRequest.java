package com.sarp.core.module.comment.model.request;

import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/17 11:29
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ReplayCommentQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "评论id")
    @NotBlank(message = "评论rootId不能为空")
    private String rootId;

}
