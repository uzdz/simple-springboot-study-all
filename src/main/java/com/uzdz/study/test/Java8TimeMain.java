package com.uzdz.study.test;

import com.uzdz.study.jpa.UserRepository;
import com.uzdz.study.module.dao.UserMapper;
import com.uzdz.study.module.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping("/java8/date")
public class Java8TimeMain {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private UserRepository userRepository;

    @GetMapping("/save")
    public String save() {

        int uzdz = userMapper.insert(User.builder().name("uzdz").createTime(LocalDateTime.now()).build());

        return "success - " + uzdz;
    }

    @GetMapping("/jpaSave")
    public String jpaSave() {

        User dongzhen = userRepository.save(User.builder().name("dongzhen").createTime(LocalDateTime.now()).build());

        return "success - " + dongzhen;
    }

    public static void main(String[] args) {

        // 创建日期Date。
        Date date = new Date();

        // 通过java1.8新增的toInstant()方法，获取Date的实例Instant对象。
        Instant instant = date.toInstant();

        // 获取系统的时区ZoneId。
        ZoneId zoneId = ZoneId.systemDefault();
        // 将日期实例按照指定时区，转换成LocalDate、LocalTime、LocalDateTime等。
        // 目前转换成了LocalDateTime
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        localDateTime.plus(1, ChronoUnit.SECONDS);

        System.out.println("Date转LocalDateTime：" + localDateTime);

        // 将LocalDateTime按照时区转换成时区时间。
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        // 利用java1.8新增的Date.from(Instant instant)，按照指定日期时间实例生成Date对象。
        Date from = Date.from(zonedDateTime.toInstant());

        System.out.println("LocalDateTime转Date：" + from);
    }
}
