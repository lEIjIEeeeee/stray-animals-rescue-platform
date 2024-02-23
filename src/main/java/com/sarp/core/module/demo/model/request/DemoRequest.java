package com.sarp.core.module.demo.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/1/22 14:01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemoRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空", groups = { DemoRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空", groups = { DemoRequest.Add.class, DemoRequest.Edit.class })
    private String name;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空", groups = { DemoRequest.Add.class, DemoRequest.Edit.class })
    private String phone;

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
