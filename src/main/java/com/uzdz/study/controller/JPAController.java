package com.uzdz.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.uzdz.study.jpa.UserRepository;
import com.uzdz.study.jpa.utils.QueryUtil;
import com.uzdz.study.module.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class JPAController {

    public static void main(String[] args) {
        User userN = new User();
        userN.setClassroom("bb");
        userN.setName("bbb");

        User user = new User();
        user.setClassroom("aaa");
        user.setName("aaa");
        user.setUser(userN);

        setUser(user);

        System.out.println(user.getUser().getName());
    }

    public static void setUser(User u) {
        User user = u.getUser();
        user.setName("uzz");
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/delete")
    public int delete() {
        return userRepository.updateFromProperties();
    }

    @GetMapping("/update")
    public int update() {
//        List<Long> ids = new ArrayList<>();
//        ids.add(1L);
//        ids.add(2L);
//        ids.add(3L);

        return userRepository.updateFromProperties1("uzdz", null);
    }

    @GetMapping("/t1/{second}")
    @Transactional(rollbackFor = Exception.class)
    public int t1(@PathVariable Integer second) throws InterruptedException {
        userRepository.updateById("uzdz", 1);
        System.out.println("我睡啦" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(second);
        System.out.println("我睡好啦" + Thread.currentThread().getName());
//        List<Long> ids = new ArrayList<>();
//        ids.add(1L);
//        ids.add(2L);
//        ids.add(3L);
        return 233;
    }




    @GetMapping("/get/{id}")
    public Object get(@PathVariable Integer id) {
        return userRepository.findById(id);
    }

    @GetMapping("/insert")
    public Object insert() {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        user.setName("uzdz");
        userRepository.saveAndFlush(user);
        return "success";
    }

//    @GetMapping("/update")
//    public Object update() {
//        User one = userRepository.getOne(1);
//        one.setName("dongzhen");
//        userRepository.saveAndFlush(one);
//        return "success";
//    }
}
