package com.sarp.core.module.notice.model.request;

import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/24 1:12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NoticeQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "公告分类id")
    private String noticeTypeId;

}
