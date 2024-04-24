package com.sarp.core.module.notice.model.response;

import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/24 0:58
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NoticeResponse extends BaseResponse {

    @ApiModelProperty(value = "公告名称")
    private String name;

    @ApiModelProperty(value = "公告图片")
    private String picUrl;

    @ApiModelProperty(value = "公告简介")
    private String desc;

}
