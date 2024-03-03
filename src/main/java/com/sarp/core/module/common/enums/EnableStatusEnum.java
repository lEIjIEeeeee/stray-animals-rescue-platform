package com.sarp.core.module.common.enums;

import lombok.*;

/**
 * @date 2024/3/1 9:33
 */

@Getter
@AllArgsConstructor
public enum EnableStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(2, "禁用");

    private final Integer code;
    private final String name;

}
