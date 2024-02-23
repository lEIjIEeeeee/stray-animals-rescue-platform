package com.sarp.core.module.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 登录用户信息表
 * @date 2024/1/26 15:54
 */

@ApiModel(description="登录用户信息表")
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User {

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="用户id")
    private String id;

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 密码盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value = "密码盐")
    private String salt;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 账户名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="账户名称")
    private String name;

    /**
     * 性别 F-女 M-男
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别 F-女 M-男")
    private String gender;

    /**
     * 用户类型 NORMAL_USER-普通用户 PLATFORM_ADMIN-平台管理员
     */
    @TableField(value = "user_type")
    @ApiModelProperty(value="用户类型 NORMAL_USER-普通用户 PLATFORM_ADMIN-平台管理员")
    private String userType;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态")
    private Integer status;

    /**
     * 创建人id
     */
    @TableField(value = "create_id")
    @ApiModelProperty(value="创建人id")
    private String createId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新人id
     */
    @TableField(value = "update_id")
    @ApiModelProperty(value="更新人id")
    private String updateId;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

}