package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/7 10:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeAdoptStatusRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "是否领养 0-否 1-是")
    @NotNull(message = "是否领养状态不能为空")
    private Integer isAdopt;

}
