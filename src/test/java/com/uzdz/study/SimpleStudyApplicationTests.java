package com.uzdz.study;

import com.uzdz.study.jpa.UserRepository;
import com.uzdz.study.kafka.KafkaController;
import com.uzdz.study.module.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles
public class SimpleStudyApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaController kafkaController;

    @Test
    public void contextLoads() {
        String uzdz = kafkaController.send("uzdz");

        System.out.println(uzdz);
    }

}
