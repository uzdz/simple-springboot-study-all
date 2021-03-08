package com.uzdz.study.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    @KafkaListener(topics = "dongzhen")
    public void listen(ConsumerRecord<?,String> record) {
        String value = record.value();
        System.out.println(value);
        System.out.println(record);
    }
}
