package com.sarp.core.module.auth.util;

import cn.hutool.core.util.RandomUtil;

/**
 * @date 2024/1/29 10:05
 */

public class AuthNoGenerateUtils {

    public static String generateAccount() {
        return RandomUtil.randomNumbers(12);
    }

    public static String generateSalt() {
        return RandomUtil.randomString(4);
    }

}
