package com.uzdz.study.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

/**
 * 交换机、队列、绑定声明工具类
 * @author: uzdz
 */
@Configuration
public class RabbitDeclareUtil {

    @Bean
    public Queue dead_queue() {
        return new Queue("dead.queue", true);
    }

    @Bean
    public DirectExchange dead_exchange() {
        return new DirectExchange("dead.exchange", true, false);
    }

    @Bean
    public Binding dead_binding(Queue dead_queue, DirectExchange dead_exchange) {
        return BindingBuilder
                .bind(dead_queue)
                .to(dead_exchange)
                .with("dead");
    }

    @Bean
    public Queue rabbit_queue() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("x-message-ttl", 10000);
        map.put("x-dead-letter-exchange", "dead.exchange");
        map.put("x-dead-letter-routing-key", "dead");
        return new Queue("rabbit.queue", true, false, false, map);
    }

    @Bean
    public DirectExchange rabbit_exchange() {
        return new DirectExchange("rabbit.exchange", true, false);
    }

    @Bean
    public Binding rabbit_binding(Queue rabbit_queue, DirectExchange rabbit_exchange) {
        return BindingBuilder
                .bind(rabbit_queue)
                .to(rabbit_exchange)
                .with("rabbit");
    }
}
