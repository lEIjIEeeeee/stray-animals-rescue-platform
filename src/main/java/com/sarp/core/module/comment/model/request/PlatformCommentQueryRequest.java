package com.sarp.core.module.comment.model.request;

import com.sarp.core.module.comment.enums.CommentTypeEnum;
import com.sarp.core.module.comment.enums.PlatformCommentSearchTypeEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @date 2024/4/19 11:29
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformCommentQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private PlatformCommentSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "评论类型")
    private CommentTypeEnum type;

    @ApiModelProperty(value = "评论开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @ApiModelProperty(value = "评论结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
