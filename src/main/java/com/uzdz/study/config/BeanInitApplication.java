package com.uzdz.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInitApplication {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanInitConfig beanInitConfig() {
        System.out.println("BeanInitConfig init");
        return new BeanInitConfig();
    }
}
