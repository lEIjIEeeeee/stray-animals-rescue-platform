package com.sarp.core.module.common.model.request;

import com.sarp.core.module.common.enums.GenderEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/4/1 16:50
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalInfoEditRequest {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "")
    @NotNull(message = "性别不能为空")
    private GenderEnum gender;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

}
