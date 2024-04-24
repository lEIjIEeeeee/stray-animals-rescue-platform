package com.sarp.core.module.notice.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2024/4/22 14:07
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeTypeDTO {

    @ApiModelProperty(value = "分类id")
    private String id;

    @ApiModelProperty(value = "分类名称")
    private String name;

}
