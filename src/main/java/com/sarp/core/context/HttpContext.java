package com.sarp.core.context;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @date 2024/1/27 14:40
 */

public class HttpContext {

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getResponse();
        } else {
            return null;
        }
    }

}
