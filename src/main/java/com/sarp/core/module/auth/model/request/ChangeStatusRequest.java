package com.sarp.core.module.auth.model.request;

import com.sarp.core.module.user.enums.UserStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/18 21:54
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusRequest {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private UserStatusEnum status;

}
