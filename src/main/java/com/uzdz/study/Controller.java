package com.uzdz.study;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class Controller {


    public static Map<String,String> goodsCategory= new ConcurrentHashMap<>();

    @GetMapping("/hello/{value1}")
    public String hello(@PathVariable(value = "value1") String name) {

        System.out.println(goodsCategory.get("uzdz"));

        goodsCategory.put("uzdz", name);
        return goodsCategory.get("uzdz");
    }

}