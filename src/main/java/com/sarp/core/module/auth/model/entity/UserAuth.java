package com.sarp.core.module.auth.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @date 2024/1/27 14:13
 *
*/

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_user_auth", autoResultMap = true)
public class UserAuth {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 用户token
     */
    @TableField(value = "token")
    private String token;

    @TableField(value = "`key`")
    private String key;

    @TableField(value = "`value`", typeHandler = JacksonTypeHandler.class)
    private JsonNode value;

    /**
     * 登录时间
     */
    @TableField(value = "create_time")
    private Date createTime;

}