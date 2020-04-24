package com.uzdz.study;

import com.easy.database.annotations.EnableMultipleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
//import tk.mybatis.spring.annotation.MapperScan;

@EnableMultipleDataSource(startup = false)
//@SpringBootApplication(exclude={MongoAutoConfiguration.class, DataSourceAutoConfiguration.class})
@SpringBootApplication(exclude={MongoAutoConfiguration.class})
@ServletComponentScan
//@MapperScan("com.uzdz.study.*.mapper")
public class SimpleStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStudyApplication.class, args);
    }
}

