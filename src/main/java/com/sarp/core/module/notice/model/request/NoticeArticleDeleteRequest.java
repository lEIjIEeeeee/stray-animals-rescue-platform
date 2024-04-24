package com.sarp.core.module.notice.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/4/21 5:10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeArticleDeleteRequest {

    @ApiModelProperty(value = "公告文章id")
    @NotBlank(message = "id不能为空")
    private String id;

}
