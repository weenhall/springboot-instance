package com.example.springbootkafka;

import com.example.springbootkafka.producer.Producer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SpringbootKafkaApplication implements CommandLineRunner,ApplicationRunner{
	@Autowired
	private Producer producer;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("服务启动完成，我是启动后执行的第一个任务");
		for(String arg : args){
			log.info("参数:{}",arg );
		}
	}

	@Override
	public void run(ApplicationArguments args) throws InterruptedException {
		log.info("参数:{}",args.getOptionValues("version"));
		int i=1;
		do {
			producer.send(i++);
			Thread.sleep(500);
		}while (i<10);
	}
}
