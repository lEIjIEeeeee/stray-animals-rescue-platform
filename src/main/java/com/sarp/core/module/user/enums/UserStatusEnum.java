package com.sarp.core.module.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/26 16:09
 */

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    //
    NORMAL(1, "正常"),
    FREEZE(2, "冻结"),
    DELETE(99, "删除")
    ;

    private final Integer code;
    private final String message;
}
