package com.sarp.core.module.common.model;

import com.sarp.core.module.common.enums.HttpResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date 2024/1/22 15:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = -6646458552916231724L;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "信息")
    private String message;

    @ApiModelProperty(value = "数据")
    private T data;

    public static <T> HttpResult<T> success() {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> success(T data) {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .data(data)
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(httpResultCode.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode, String message) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(message)
                         .build();
    }

}
