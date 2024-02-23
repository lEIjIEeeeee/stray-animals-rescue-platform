package com.sarp.core.exception;

import com.sarp.core.module.common.enums.HttpResultCode;
import lombok.Data;

/**
 * @date 2024/1/22 14:39
 */

@Data
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -3313594302542247136L;

    private HttpResultCode httpResultCode;

    public BizException(HttpResultCode httpResultCode) {
        super(httpResultCode.getMessage());
        this.httpResultCode = httpResultCode;
    }

    public BizException(HttpResultCode httpResultCode, String message) {
        super(message);
        this.httpResultCode = httpResultCode;
    }

}
