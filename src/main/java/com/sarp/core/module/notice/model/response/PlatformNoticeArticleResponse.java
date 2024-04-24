package com.sarp.core.module.notice.model.response;

import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/4/21 4:32
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformNoticeArticleResponse extends BaseResponse {

    @ApiModelProperty(value = "公告文章名称")
    private String name;

    @ApiModelProperty(value = "公告类型id")
    private String noticeTypeId;

    @ApiModelProperty(value = "公告类型名称")
    private String noticeTypeName;

    @ApiModelProperty(value = "公告文章封面")
    private String picUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "描述说明")
    private String desc;

}
