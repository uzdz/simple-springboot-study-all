package com.uzdz.practices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzdz.practices.module.dao.UserMapper;
import com.uzdz.practices.module.entity.User;
import com.uzdz.practices.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
public class PracticesApplication {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

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

    @GetMapping("/set/{key}/{value}")
    public String set(@PathVariable("key") String key, @PathVariable("value") String value) {
        return jedisUtil.set(key, value);
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable(name = "key") String key) {
        return jedisUtil.get(key);
    }
}
