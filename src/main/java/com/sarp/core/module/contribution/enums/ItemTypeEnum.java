package com.sarp.core.module.contribution.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/3/30 18:01
 */

@Getter
@AllArgsConstructor
public enum ItemTypeEnum {
    //
    FOOD(1, "食物"),
    CLOTHES(2, "衣服"),
    TOY(3, "玩具"),
    OTHERS(99, "其他")
    ;

    private final Integer code;
    private final String name;
}
