package com.sarp.core.animal;

import com.sarp.core.BaseTest;
import com.sarp.core.module.animal.util.AnimalNoGenerateUtils;
import org.junit.Test;

/**
 * @date 2024/1/30 16:10
 */

public class AnimalNoGenerateUtilsTest extends BaseTest {

    @Test
    public void generateAnimalNoTest() {
        String animalNo = AnimalNoGenerateUtils.generateAnimalNo();
        System.out.println(animalNo);
    }

}
