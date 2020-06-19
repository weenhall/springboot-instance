package com.example.demo;

import com.example.demo.pojo.Book;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class SpringbootRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedisApplication.class, args);
	}


	@Bean
	public RedisTemplate<String,Book> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<String,Book> template=new RedisTemplate<>();
		template.setConnectionFactory(factory);
		//Add some specific configuration here,Key serializers, etc
		RedisSerializer<String> redisSerializer=new StringRedisSerializer();
		Jackson2JsonRedisSerializer<Book> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<Book>(Book.class);
		ObjectMapper mapper=new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(mapper);

		template.setKeySerializer(redisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		return template;
	}
}
