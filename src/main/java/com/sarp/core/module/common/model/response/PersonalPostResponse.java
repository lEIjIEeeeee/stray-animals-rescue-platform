package com.sarp.core.module.common.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * @date 2024/3/12 11:12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonalPostResponse extends BaseResponse {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "摘要")
    private String postAbstract;

    @ApiModelProperty(value = "业务类型")
    private Integer bizType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private String createId;

    @ApiModelProperty(value = "创建人名称")
    private String createUser;

}
