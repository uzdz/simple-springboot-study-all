package com.uzdz.study.rabbit;

//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 发送消息
// * @author uzdz
// */
//@RestController
//public class SendMessage {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @GetMapping("/send")
//    public void send() {
//        rabbitTemplate.convertAndSend(
//                "rabbit.exchange",
//                "rabbit",
//                new Message("hello rabbitMQ".getBytes(), new MessageProperties())
//        );
//    }
//}
