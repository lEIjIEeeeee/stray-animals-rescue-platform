package com.sarp.core.module.notice.model.response;

import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/20 18:17
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformNoticeTypeResponse extends BaseResponse {

    @ApiModelProperty(value = "公告类型名称")
    private String name;

    @ApiModelProperty(value = "简介")
    private String desc;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "启用状态")
    private Integer status;

}
