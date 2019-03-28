package com.uzdz.practices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzdz.practices.module.dao.UserMapper;
import com.uzdz.practices.module.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PracticesApplication {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(PracticesApplication.class, args);
    }

    @GetMapping("/get")
    public String get() throws Exception {
        User user = userMapper.selectById(1);
        return user == null ? "error" : objectMapper.writeValueAsString(user);
    }

    @GetMapping("/update/{id}/{name}")
    public boolean update(@PathVariable("id") Integer id, @PathVariable("name") String name) {
        return userMapper.updateUser(id, name) >= 1;
    }
}
