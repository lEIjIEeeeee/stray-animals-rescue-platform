package com.sarp.core.module.post.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/31 11:56
 */

@Getter
@AllArgsConstructor
public enum PostLaunchTypeEnum {
    //
    PERSON(1, "个人"),
    PLATFORM(2, "平台")
    ;

    private final Integer code;
    private final String name;
}
