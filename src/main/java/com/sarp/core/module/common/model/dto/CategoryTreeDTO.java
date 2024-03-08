package com.sarp.core.module.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @date 2024/3/1 15:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryTreeDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "子节点数据")
    private List<CategoryTreeDTO> children;

}
