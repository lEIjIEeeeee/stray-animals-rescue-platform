package com.sarp.core.module.animal.model.request;

import com.sarp.core.module.common.model.request.BaseQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/3/25 15:38
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AnimalQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索内容")
    private String searchContent;

    @ApiModelProperty(value = "是否领养")
    private Integer isAdopt;

    @ApiModelProperty(value = "是否遗失")
    private Integer isLost;

    @ApiModelProperty(value = "动物类目id集合")
    private String categoryIds;

}
