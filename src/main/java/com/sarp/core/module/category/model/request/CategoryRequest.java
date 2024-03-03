package com.sarp.core.module.category.model.request;

import com.sarp.core.module.common.enums.EnableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/1 9:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

    @ApiModelProperty(value = "类目id")
    @NotBlank(message = "id不能为空", groups = { CategoryRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "父级id")
    @NotBlank(message = "父级不能为空", groups = { CategoryRequest.Add.class, CategoryRequest.Edit.class })
    private String pid;

    @ApiModelProperty(value = "类目名称")
    @NotBlank(message = "类目名称不能为空", groups = { CategoryRequest.Add.class, CategoryRequest.Edit.class })
    private String name;

    @ApiModelProperty(value = "状态 1-启用 2-禁用")
    @NotNull(message = "状态不能为空", groups = { CategoryRequest.Add.class, CategoryRequest.Edit.class })
    private EnableStatusEnum status;

    @ApiModelProperty(value = "排序")
    @NotNull(message = "排序不能为空", groups = { CategoryRequest.Add.class, CategoryRequest.Edit.class })
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 新增校验组
     */
    public interface Add {
    }

    /**
     * 修改校验组
     */
    public interface Edit {
    }

}
