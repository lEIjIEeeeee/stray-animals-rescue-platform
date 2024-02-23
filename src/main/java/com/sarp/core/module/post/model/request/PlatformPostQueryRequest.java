package com.sarp.core.module.post.model.request;

import com.sarp.core.module.post.enums.PostLaunchTypeEnum;
import com.sarp.core.module.post.enums.PostStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @date 2024/1/31 14:54
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformPostQueryRequest extends PostQueryRequest {

    @ApiModelProperty(value = "审核状态")
    private PostStatusEnum status;

    @ApiModelProperty(value = "发起方式")
    private PostLaunchTypeEnum launchType;

    @ApiModelProperty(value = "审核开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditStartTime;

    @ApiModelProperty(value = "审核结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditEndTime;

}
