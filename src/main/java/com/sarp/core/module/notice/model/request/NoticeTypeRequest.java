package com.sarp.core.module.notice.model.request;

import com.sarp.core.module.common.enums.EnableStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/4/20 19:23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeTypeRequest {

    @ApiModelProperty(value = "公告类型id")
    @NotBlank(message = "id不能为空", groups = { NoticeTypeRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "公告类型名称")
    @NotBlank(message = "名称不能为空", groups = { NoticeTypeRequest.Add.class, NoticeTypeRequest.Edit.class })
    private String name;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空", groups = { NoticeTypeRequest.Add.class, NoticeTypeRequest.Edit.class })
    private EnableStatusEnum status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

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
