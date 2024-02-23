package com.sarp.core.module.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/1/22 16:17
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseQueryRequest {

    @ApiModelProperty(value = "页码")
    @Min(value = 1, groups = { BaseQueryRequest.ListPage.class })
    @NotNull(message = "pageNo不能为空", groups = { BaseQueryRequest.ListPage.class })
    private Long pageNo;

    @ApiModelProperty(value = "页大小")
    @Min(value = 1, groups = { BaseQueryRequest.ListPage.class })
    @Max(value = 100, groups = { BaseQueryRequest.ListPage.class })
    @NotNull(message = "pageSize不能为空", groups = { BaseQueryRequest.ListPage.class })
    private Long pageSize;

    /**
     * 分页校验组
     */
    public interface ListPage {
    }

}
