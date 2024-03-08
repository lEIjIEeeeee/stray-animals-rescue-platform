package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/7 10:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeLostStatusRequest {

    @ApiModelProperty(value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "是否遗失 0-否 1-是")
    @NotNull(message = "是否遗失状态不能为空")
    private Integer isLost;

}
