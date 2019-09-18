package com.uzdz.study;

import com.uzdz.study.jpa.UserRepository;
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

    @Test
    public void contextLoads() {
        User dongzhen = userRepository.save(User.builder().name("dongzhe111zzn").createTime(LocalDateTime.now()).build());

        System.out.println(dongzhen);
    }

}
