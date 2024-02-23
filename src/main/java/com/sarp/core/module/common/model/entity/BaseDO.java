package com.sarp.core.module.common.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/1/22 14:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseDO {

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    public static final String ID = "id";

    @ApiModelProperty(value = "创建人id")
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private String createId;
    public static final String CREATE_ID = "createId";

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    public static final String CREATE_TIME = "createTime";

    @ApiModelProperty(value = "更新人id")
    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    private String updateId;
    public static final String UPDATE_ID = "updateId";

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    public static final String UPDATE_TIME = "updateTime";

    @ApiModelProperty(value = "逻辑删除 0-否 1-是")
    @TableField(value = "is_del", fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDel;
    public static final String IS_DEL = "isDel";

}
