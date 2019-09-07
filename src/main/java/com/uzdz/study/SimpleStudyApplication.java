package com.uzdz.study;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.easy.database.annotations.EnableMultipleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableMultipleDataSource
@SpringBootApplication
public class SimpleStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStudyApplication.class, args);
    }
}

