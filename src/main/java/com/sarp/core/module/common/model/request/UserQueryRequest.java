package com.sarp.core.module.common.model.request;

import com.sarp.core.module.user.enums.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/3/8 21:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserQueryRequest {

    @ApiModelProperty(value = "用户类型")
    private UserTypeEnum userType;

}
