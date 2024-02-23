package com.sarp.core.module.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2024/1/22 14:32
 */

@Getter
@AllArgsConstructor
public enum HttpResultCode {

    //0~999 系统相关错误
    SUCCESS(0, "成功"),
    SYSTEM_ERROR(999, "系统异常"),

    //1000~1999 参数相关错误
    PARAM_VALIDATED_FAILED(1000, "参数校验失败"),
    TOKEN_VALIDATED_FAILED(1001, "token校验失败"),
    TOKEN_EXPIRED(1002, "token已过期"),

    //2000~2999 用户相关错误
    USER_ACCOUNT_OR_PASSWORD_VALIDATE_FAILED(2000, "用户账号密码校验失败"),
    USER_ACCOUNT_NOT_EXIST(2001, "用户账号不存在"),
    USER_STATUS_ERROR(2002, "用户状态异常"),

    //3000~3999 业务相关错误
    BIZ_EXCEPTION(3000, "业务异常"),
    BIZ_DATA_EXCEPTION(3001, "业务数据异常"),
    DATA_NOT_EXISTED(3002, "数据不存在"),
    DATA_EXISTED(3003, "数据已存在")
    ;

    private final Integer code;
    private final String message;

}
