package com.sarp.core.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/3/26 9:52
 */

@Getter
@AllArgsConstructor
public enum UploadBizTypeEnum {
    AVATAR("avatar", "头像"),
    ANIMAL("animal", "宠物"),
    POST("post", "帖子"),
    CONTRIBUTION("contribution", "捐助"),
    COMMON("common", "公共")
    ;

    private String code;
    private String name;
}
