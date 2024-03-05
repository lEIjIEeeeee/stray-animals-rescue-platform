package com.sarp.core.module.post.model.response;

import com.sarp.core.module.common.model.response.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/1/31 17:29
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PostResponse extends BaseResponse {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "动物名称")
    private String animalName;

    @ApiModelProperty(value = "动物类目id")
    private String categoryId;

    @ApiModelProperty(value = "动物类目名称")
    private String categoryName;

    @ApiModelProperty(value = "业务类型")
    private Integer bizType;

    @ApiModelProperty(value = "文章摘要")
    private String postAbstract;

    @ApiModelProperty(value = "创建人id")
    private String createId;

    @ApiModelProperty(value = "创建用户名称")
    private String createUser;

}
