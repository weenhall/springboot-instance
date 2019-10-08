package com.example.springbootkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by ween on 2019/3/21
 */
@Component
public class ConsumerDemo {

    @KafkaListener(topics = {"outputs_info"})
    public void listen(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage=Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()){
            Object Message=kafkaMessage.get();
            System.out.println("--record"+record);
            System.out.println("--message"+Message);
        }
    }

}
