package com.example.springbootkafka.producer;

import com.alibaba.fastjson.JSON;
import com.example.springbootkafka.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ween on 2019/3/21
 */
@Component
public class Producer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(int i){
        Message message=new Message();
        Point point=new Point("001",i,i);
        message.setType(1);
        List<Point> list=new ArrayList<>();
        list.add(point);
        message.setList(list);
        kafkaTemplate.send("test", JSON.toJSONString(message));
    }
}
