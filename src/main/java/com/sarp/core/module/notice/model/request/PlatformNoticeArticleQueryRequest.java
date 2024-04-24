package com.sarp.core.module.notice.model.request;

import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/21 4:27
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformNoticeArticleQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "公告类型id")
    private String noticeTypeId;

}
