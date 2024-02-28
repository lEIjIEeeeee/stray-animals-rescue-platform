package com.sarp.core.module.post.model.request;

import com.sarp.core.module.common.enums.AuditResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/2/28 11:04
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostAuditRequest {

    @ApiModelProperty(value = "帖子id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "审核操作结果")
    @NotNull(message = "操作结果不能为空")
    private AuditResultEnum auditResult;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
