package com.sarp.core.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/30 15:31
 */

@Getter
@AllArgsConstructor
public enum BizTypeEnum {
    //
    ADOPT_BIZ(1, "领养"),
    LOSS_BIZ(2, "挂失"),
    OTHER(99, "其他")
    ;

    private final Integer code;
    private final String name;
}
