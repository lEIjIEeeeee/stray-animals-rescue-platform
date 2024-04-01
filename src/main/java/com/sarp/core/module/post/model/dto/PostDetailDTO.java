package com.sarp.core.module.post.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @date 2024/3/31 21:22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetailDTO {

    @ApiModelProperty(value = "帖子id")
    private String id;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "作者id")
    private String createId;

    @ApiModelProperty(value = "作者头像")
    private String avatar;

    @ApiModelProperty(value = "作者昵称")
    private String nickName;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "动物类目")
    private String categoryName;

    @ApiModelProperty(value = "帖子分类")
    private Integer bizType;

    @ApiModelProperty(value = "正文内容")
    private String content;

    @ApiModelProperty(value = "帖子图片")
    private String picUrl;

}
