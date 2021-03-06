package com.uzdz.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/kafka/send")
    public String send(String name) {
        kafkaTemplate.send("mytopic", name);
        return name;
    }
}
