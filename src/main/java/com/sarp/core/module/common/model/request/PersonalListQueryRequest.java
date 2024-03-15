package com.sarp.core.module.common.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/3/9 11:45
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonalListQueryRequest extends BaseQueryRequest {

    @ApiModelProperty(value = "搜索关键字")
    private String searchContent;

}
