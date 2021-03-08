package com.uzdz.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ：uzdz
 */
public class DBController {


    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    @GetMapping("jdbc")
    public void jdbc() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select id as '1', name as '2', create_time as '3' from t_user");

        List<Object> k = maps.stream().map((data) -> data.get("c")).collect(Collectors.toList());

        System.out.println(k.get(0));
    }

}
