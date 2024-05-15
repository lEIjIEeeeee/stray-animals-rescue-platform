package com.sarp.core.module.animal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * @date 2024/5/16 1:41
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AdoptReapplyRequest extends AdoptApplyRequest{

    @ApiModelProperty(value = "记录id")
    @NotBlank(message = "记录id不能为空")
    private String id;

}
