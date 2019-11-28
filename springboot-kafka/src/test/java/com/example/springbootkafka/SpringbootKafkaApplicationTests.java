package com.example.springbootkafka;

import com.example.springbootkafka.consumer.ConsumerDemo;
import com.example.springbootkafka.producer.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootKafkaApplicationTests {

	@Autowired
	private Producer producer;

	@Autowired
	private ConsumerDemo consumerDemo;

	@Test
	public void send() throws InterruptedException {
		for(int i=0;i<5;i++){
			producer.send(i);
			Thread.sleep(1000);
		}
	}

}
