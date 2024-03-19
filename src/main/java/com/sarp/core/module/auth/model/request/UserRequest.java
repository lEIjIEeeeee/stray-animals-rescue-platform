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
 * @date 2024/3/19 9:51
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "id不能为空", groups = { UserRequest.Edit.class })
    private String id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    @NotBlank(message = "用户昵称不能为空", groups = { UserRequest.Add.class, UserRequest.Edit.class })
    private String nickName;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空", groups = { UserRequest.Add.class })
    private String password;

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "确认密码不能为空", groups = { UserRequest.Add.class })
    private String confirmPassword;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空", groups = { UserRequest.Add.class, UserRequest.Edit.class })
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空", groups = { UserRequest.Add.class, UserRequest.Edit.class })
    private String gender;

    @ApiModelProperty(value = "用户类型")
    @NotBlank(message = "用户类型不能为空", groups = { UserRequest.Add.class, UserRequest.Edit.class })
    private String userType;

    @ApiModelProperty(value = "用户状态")
    @NotNull(message = "状态不能为空", groups = { UserRequest.Add.class, UserRequest.Edit.class })
    private UserStatusEnum status;

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
