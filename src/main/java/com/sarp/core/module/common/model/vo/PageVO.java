package com.sarp.core.module.common.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @date 2024/1/22 16:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageVO<T> {

    @ApiModelProperty(value = "页码")
    private Long pageNo;

    @ApiModelProperty(value = "页大小")
    private Long pageSize;

    @ApiModelProperty(value = "总数据量")
    private Long total;

    @ApiModelProperty(value = "数据列表")
    private List<T> dataList;

}
