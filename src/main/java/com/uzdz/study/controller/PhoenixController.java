package com.uzdz.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/phoenix")
public class PhoenixController {

    @Resource
    JdbcTemplate jdbcPhoenixTemplate;

    @GetMapping("/get")
    public String select() {
        List<Map<String, Object>> select = jdbcPhoenixTemplate.queryForList("select NAME as V1, HOME as V2 from USER");
        return "success";
    }
}
