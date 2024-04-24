package com.sarp.core.module.comment.model.request;

import com.sarp.core.module.comment.enums.CommentSortTypeEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/4/8 15:53
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "帖子id")
    @NotBlank(message = "帖子id不能为空", groups = { BaseQueryRequest.ListPage.class })
    private String postId;

    @ApiModelProperty(value = "回复评论展示最大条数")
    @Min(value = 1, groups = { BaseQueryRequest.ListPage.class })
    @Max(value = 10, groups = { BaseQueryRequest.ListPage.class })
    @NotNull(message = "replayPageSize不能为空", groups = { BaseQueryRequest.ListPage.class })
    private Long replayPageSize;

    @ApiModelProperty(value = "排序方式")
    @NotNull(message = "排序方式不能为空", groups = { BaseQueryRequest.ListPage.class })
    private CommentSortTypeEnum sortType;

}
