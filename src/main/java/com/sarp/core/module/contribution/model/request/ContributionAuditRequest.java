package com.sarp.core.module.contribution.model.request;

import com.sarp.core.module.common.enums.AuditResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/31 2:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContributionAuditRequest {

    @ApiModelProperty(value = "记录id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "审核结果")
    @NotNull(message = "审核结果不能为空")
    private AuditResultEnum auditResult;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
