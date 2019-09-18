package com.uzdz.study;

import com.easy.database.annotations.EnableMultipleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMultipleDataSource
@SpringBootApplication
public class SimpleStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStudyApplication.class, args);
    }
}

