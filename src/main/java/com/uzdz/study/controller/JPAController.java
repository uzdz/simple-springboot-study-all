package com.uzdz.study.controller;

import com.uzdz.study.jpa.UserRepository;
import com.uzdz.study.module.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class JPAController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/get")
    public Object get() {
        return userRepository.findById(1);
    }

    @GetMapping("/insert")
    public Object insert() {
        User user = new User();
        user.setCreateTime(LocalDateTime.now());
        user.setName("uzdz");
        userRepository.saveAndFlush(user);
        return "success";
    }

    @GetMapping("/update")
    public Object update() {
        User one = userRepository.getOne(1);
        one.setName("dongzhen");
        userRepository.saveAndFlush(one);
        return "success";
    }
}
