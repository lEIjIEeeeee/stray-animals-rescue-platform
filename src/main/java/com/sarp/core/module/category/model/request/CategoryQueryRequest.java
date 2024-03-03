package com.sarp.core.module.category.model.request;

import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/3/1 15:05
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CategoryQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "pid不能为空", groups = { BaseQueryRequest.ListPage.class })
    private String pid;

}
