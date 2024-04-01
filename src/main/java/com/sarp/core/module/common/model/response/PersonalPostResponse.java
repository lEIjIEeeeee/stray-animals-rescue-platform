package com.sarp.core.module.common.model.response;

import com.sarp.core.module.post.model.response.PostResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2024/3/12 11:12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PersonalPostResponse extends PostResponse {

    @ApiModelProperty(value = "状态")
    private Integer status;

}
