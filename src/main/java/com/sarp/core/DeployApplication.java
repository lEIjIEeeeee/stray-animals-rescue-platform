package com.sarp.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author wlj
 * @date 2024/1/22 11:25
 */

@SpringBootApplication
@MapperScan("com.sarp.core")
public class DeployApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DeployApplication.class).run(args);
    }
}
