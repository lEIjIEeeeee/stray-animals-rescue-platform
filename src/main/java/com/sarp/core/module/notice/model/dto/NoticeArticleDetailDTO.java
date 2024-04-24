package com.sarp.core.module.notice.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/4/23 5:27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeArticleDetailDTO {

    @ApiModelProperty(value = "公告文章id")
    private String id;

    @ApiModelProperty(value = "公告文章名称")
    private String name;

    @ApiModelProperty(value = "公告类型id")
    private String noticeTypeId;

    @ApiModelProperty(value = "公告类型名称")
    private String noticeTypeName;

    @ApiModelProperty(value = "公告文章封面")
    private String picUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "简介")
    private String desc;

    @ApiModelProperty(value = "公告内容")
    private String content;

}
