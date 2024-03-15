package com.sarp.core.module.animal.model.entity;

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
 * 流浪动物领养信息表
 * @date 2024/3/14 16:36
 */
@ApiModel(description="流浪动物领养信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_animal_adopt")
public class AnimalAdopt extends BaseDO {

    /**
     * 流浪动物id
     */
    @TableField(value = "animal_id")
    @ApiModelProperty(value="流浪动物id")
    private String animalId;

    /**
     * 帖子id
     */
    @TableField(value = "post_id")
    @ApiModelProperty(value="帖子id")
    private String postId;

    /**
     * 领养人id
     */
    @TableField(value = "adopt_user")
    @ApiModelProperty(value="领养人id")
    private String adoptUser;

    /**
     * 领养结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value="领养结束时间")
    private Date endTime;

}