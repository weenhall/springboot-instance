package com.example.springbootactiviti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SpringbootActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootActivitiApplication.class, args);
	}
}
