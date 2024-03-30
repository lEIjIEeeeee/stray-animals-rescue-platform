package com.sarp.core.module.animal.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/3/29 11:02
 */

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {
    AUDIT_WAIT(1, "待审核"),
    AUDIT_PASS(2, "审核通过"),
    AUDIT_REJECT(3, "审核拒绝"),
    ;

    private final Integer code;
    private final String name;
}
