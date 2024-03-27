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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @date 2024/3/25 9:12
 *
*/

/**
 * 流浪动物信息表
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
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 宠物编号
     */
    @TableField(value = "animal_no")
    @ApiModelProperty(value = "宠物编号")
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
     * 性别 F-雌性 M-雄性
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别 F-雌性 M-雄性")
    private String gender;

    /**
     * 出生日期
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    /**
     * 体重（千克）
     */
    @TableField(value = "weight")
    @ApiModelProperty(value = "体重（千克）")
    private BigDecimal weight;

    /**
     * 是否领养 0-否 1-是
     */
    @TableField(value = "is_adopt")
    @ApiModelProperty(value = "是否领养 0-否 1-是")
    private Integer isAdopt;

    /**
     * 是否遗失 0-否 1-是
     */
    @TableField(value = "is_lost")
    @ApiModelProperty(value = "是否遗失 0-否 1-是")
    private Integer isLost;

    /**
     * 描述信息
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "描述信息")
    private String desc;

}