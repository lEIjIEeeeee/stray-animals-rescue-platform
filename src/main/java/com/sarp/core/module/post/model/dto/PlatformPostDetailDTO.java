package com.sarp.core.module.post.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/4/2 10:08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PlatformPostDetailDTO extends PostDetailDTO {

    @ApiModelProperty(value = "帖子摘要")
    private String postAbstract;

    @ApiModelProperty(value = "审核人名称")
    private String auditorName;

    @ApiModelProperty(value = "审核时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

}
