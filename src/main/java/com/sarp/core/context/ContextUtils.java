package com.sarp.core.context;

import com.sarp.core.exception.BizException;
import com.sarp.core.module.auth.model.dto.LoginUser;
import com.sarp.core.module.common.enums.HttpResultCode;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

/**
 * @date 2024/1/26 16:42
 */

public class ContextUtils {

    private static final String CONTEXT = "context";

    private static final NamedThreadLocal<Context> contextContainer = new NamedThreadLocal<>(CONTEXT);

    public static Context getContext() {
        return contextContainer.get();
    }

    public static void setContext(Context context) {
        contextContainer.set(context);
    }

    public static LoginUser getCurrentUser() {
        return Optional.ofNullable(contextContainer.get())
                       .map(Context::getCurrentUser)
                       .orElse(null);
    }

    public static String getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                       .map(LoginUser::getId)
                       .orElseThrow(() -> new BizException(HttpResultCode.SYSTEM_ERROR, "当前用户ID信息获取失败"));
    }

    public static void clear() {
        contextContainer.remove();
    }

}
