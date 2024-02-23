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

/**
 * 流浪动物信息表
 * @date 2024/1/30 17:55
 */
@ApiModel(description = "流浪动物信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_animal")
public class Animal extends BaseDO {

    /**
     * 动物编号
     */
    @TableField(value = "animal_no")
    @ApiModelProperty(value = "动物编号")
    private String animalNo;

    /**
     * 分类id
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "分类id")
    private String categoryId;

    /**
     * 当前主人id
     */
    @TableField(value = "owner_id")
    @ApiModelProperty(value = "当前主人id")
    private String ownerId;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 性别 F-雌性 M-雄性
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 F-雌性 M-雄性")
    private String gender;

    /**
     * 是否领养 0-否 1-是
     */
    @TableField(value = "is_adopt")
    @ApiModelProperty(value = "是否领养 0-否 1-是")
    private Integer isAdopt;

    /**
     * 是否挂失 0-否 1-是
     */
    @TableField(value = "is_loss")
    @ApiModelProperty(value = "是否挂失 0-否 1-是")
    private Integer isLoss;

    /**
     * 描述信息
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "描述信息")
    private String desc;

}