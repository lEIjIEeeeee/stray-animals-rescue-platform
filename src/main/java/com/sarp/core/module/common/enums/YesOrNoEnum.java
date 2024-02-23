package com.sarp.core.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/22 15:33
 */

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {
    N(0, "否"),
    Y(1, "是");

    private final Integer code;
    private final String name;
}
