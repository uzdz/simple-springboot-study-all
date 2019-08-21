package com.uzdz.study.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收消息
 * @author uzdz
 */
@Component
public class GetMessage {

    @RabbitListener(bindings ={@QueueBinding(
            value = @Queue(value = "dead.queue", durable = "true"),
            exchange =@Exchange(value = "dead.exchange"),
            key = "dead")})
    public void handleMessage(Message message){
        System.out.println("接收到消息：" + new String(message.getBody()));
    }
}
