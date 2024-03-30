package com.sarp.core.module.adopt.model.request;

import com.sarp.core.module.adopt.enums.AdoptSearchTypeEnum;
import com.sarp.core.module.animal.enums.AuditStatusEnum;
import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @date 2024/3/29 17:03
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformAdoptQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索类型")
    private AdoptSearchTypeEnum searchType;

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "状态")
    private AuditStatusEnum status;

    @ApiModelProperty(value = "审核开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditStartTime;

    @ApiModelProperty(value = "审核结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditEndTime;

    @ApiModelProperty(value = "领养开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adoptStartDate;

    @ApiModelProperty(value = "领养结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adoptEndDate;

    @ApiModelProperty(value = "申请开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyStartTime;

    @ApiModelProperty(value = "申请结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyEndTime;

}
