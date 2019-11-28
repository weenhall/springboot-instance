package com.example.springbootkafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootKafkaApplication implements CommandLineRunner,ApplicationRunner{

	private static Logger logger= LoggerFactory.getLogger(SpringbootKafkaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成，我是启动后执行的第一个任务");
		for(String arg : args){
			logger.info("参数:{}",arg );
		}
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("参数:{}",args.getOptionValues("version"));
	}
}
