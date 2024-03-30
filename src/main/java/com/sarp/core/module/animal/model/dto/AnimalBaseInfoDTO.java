package com.sarp.core.module.animal.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @date 2024/3/7 17:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalBaseInfoDTO {

    /**
     * 主键id
     */
    private String id;

    /**
     * 宠物名称
     */
    private String name;

    /**
     * 宠物编号
     */
    private String animalNo;

    /**
     * 动物类目id
     */
    private String categoryId;

    /**
     * 动物类目名称
     */
    private String categoryName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 体重
     */
    private BigDecimal weight;

    /**
     * 宠物主人id
     */
    private String ownerId;

    /**
     * 宠物主人名称
     */
    private String ownerName;

    /**
     * 宠物主人联系电话
     */
    private String ownerPhone;

    /**
     * 是否领养
     */
    private Integer isAdopt;

    /**
     * 是否遗失
     */
    private Integer isLost;

    /**
     * 宠物图片
     */
    private String picUrl;

    /**
     * 描述信息
     */
    private String desc;

}
