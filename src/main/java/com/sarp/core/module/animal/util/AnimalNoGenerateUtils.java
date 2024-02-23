package com.sarp.core.module.animal.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

/**
 * @date 2024/1/30 15:58
 */

public class AnimalNoGenerateUtils {

    public static String generateAnimalNo() {
        String currentDate = DateUtil.format(DateUtil.date(), "yyMMdd");
        return currentDate + RandomUtil.randomNumbers(6);
    }

}
