package com.sarp.core.module.contribution.model.request;

import com.sarp.core.module.animal.enums.AuditStatusEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import com.sarp.core.module.contribution.enums.ContributionSearchTypeEnum;
import com.sarp.core.module.contribution.enums.ItemTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @date 2024/3/30 21:07
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformContributionQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private ContributionSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "捐助物类型")
    private ItemTypeEnum itemType;

    @ApiModelProperty(value = "审核状态")
    private AuditStatusEnum status;

    @ApiModelProperty(value = "审核开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditStartTime;

    @ApiModelProperty(value = "审核结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditEndTime;

    @ApiModelProperty(value = "申请开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;

    @ApiModelProperty(value = "申请结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;

}
