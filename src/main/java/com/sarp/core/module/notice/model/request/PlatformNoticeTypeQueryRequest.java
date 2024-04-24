package com.sarp.core.module.notice.model.request;

import com.sarp.core.module.common.enums.EnableStatusEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/20 18:23
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformNoticeTypeQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

}
