package com.sarp.core.module.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sarp.core.module.common.model.entity.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 用户扩展信息表
 * @date 2024/1/26 15:55
 */

@ApiModel(description="用户扩展信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_member")
public class Member extends BaseDO {

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像")
    private String avatar;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    @ApiModelProperty(value="昵称")
    private String nickName;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value="手机号")
    private String phone;

    /**
     * 性别 F-女 M-男
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别 F-女 M-男")
    private String gender;

    /**
     * 状态 1-正常 2-冻结 99-删除
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态 1-正常 2-冻结 99-删除")
    private Integer status;

    /**
     * 最近登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="最近登录时间")
    private Date lastLoginTime;

}