package com.sarp.core.module.notice.model.entity;

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
 * 公告信息表
 * @date 2024/4/20 18:58
 */

@ApiModel(description="公告信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tbl_notice_article")
public class NoticeArticle extends BaseDO {

    /**
     * 公告类型id
     */
    @TableField(value = "notice_type_id")
    @ApiModelProperty(value="公告类型id")
    private String noticeTypeId;

    /**
     * 公告文章名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="公告名称")
    private String name;

    /**
     * 公告文章内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value = "公告文章内容")
    private String content;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 公告封面主图
     */
    @TableField(value = "pic_url")
    @ApiModelProperty(value="公告封面主图")
    private String picUrl;

    /**
     * 描述说明
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value="描述说明")
    private String desc;

}