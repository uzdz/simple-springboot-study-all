package com.uzdz.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitApplication {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanInitConfig beanInitConfig() {
        return new BeanInitConfig();
    }
}
