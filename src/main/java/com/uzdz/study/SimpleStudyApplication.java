package com.uzdz.study;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.easy.database.annotations.EnableMultipleDataSource;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@EnableMultipleDataSource(startup = false)
@SpringBootApplication(exclude={
        KafkaAutoConfiguration.class,
        MongoAutoConfiguration.class,
        ElasticsearchAutoConfiguration.class,
        RabbitAutoConfiguration.class})
@ServletComponentScan
@MapperScan("com.uzdz.study.*.mapper")
@RestController
public class SimpleStudyApplication extends  Thread {

    public static void main(String[] args) {
        SpringApplication.run(SimpleStudyApplication.class, args);
    }

    @GetMapping("/do")
    public String doMDC() throws InterruptedException {
        MDC.put("uzdz", "dongzhen");
        System.out.println("ccc" + Thread.currentThread().getName());
        MDC.setContextMap(MDC.getCopyOfContextMap());
        CompletableFuture.runAsync(() -> {
            System.out.println("bbbb" + Thread.currentThread().getName());
            System.out.println(MDC.get("uzdz"));
        }).whenComplete((t, v) -> {

            System.out.println(t);
            System.out.println(v);
            System.out.println("ccc" + Thread.currentThread().getName());
            System.out.println(MDC.get("uzdz"));
        });
        System.out.println("ccc" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(5);
        System.out.println(MDC.get("uzdz"));
        return MDC.get("uzdz");
    }
}

