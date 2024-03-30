package com.sarp.core.module.media.model.entity;

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
 * 媒体信息表
 * @date 2024/3/27 20:41
 */

@ApiModel(description="媒体信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_media")
public class Media extends BaseDO {

    /**
     * 服务id
     */
    @TableField(value = "service_id")
    @ApiModelProperty(value="服务id")
    private String serviceId;

    /**
     * 服务类型 
     */
    @TableField(value = "service_type")
    @ApiModelProperty(value="服务类型 ")
    private String serviceType;

    /**
     * 图片名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="图片名称")
    private String name;

    /**
     * 图片url
     */
    @TableField(value = "pic_url")
    @ApiModelProperty(value="图片url")
    private String picUrl;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

}