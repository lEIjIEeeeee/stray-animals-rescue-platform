package com.sarp.core.module.adopt.model.request;

import com.sarp.core.module.common.enums.AuditResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @date 2024/3/30 5:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdoptAuditRequest {

    @ApiModelProperty(value = "记录id")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty(value = "审核结果")
    @NotNull(message = "审核结果不能为空")
    private AuditResultEnum auditResult;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
