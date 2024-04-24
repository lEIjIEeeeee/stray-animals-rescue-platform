package com.sarp.core.module.notice.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/21 2:23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeTypeDeleteRequest {

    @ApiModelProperty(value = "公告类型id")
    @NotBlank(message = "id不能为空")
    private String id;

}
