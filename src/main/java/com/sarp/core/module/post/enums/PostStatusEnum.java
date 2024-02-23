package com.sarp.core.module.post.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/30 17:20
 */

@Getter
@AllArgsConstructor
public enum PostStatusEnum {
    //
    AUDIT_WAIT(1, "待审核"),
    AUDIT_PASS(2, "审核通过"),
    AUDIT_REJECT(3, "审核拒绝"),
    CLOSED(4, "已关闭")
    ;

    private final Integer code;
    private final String name;
}
