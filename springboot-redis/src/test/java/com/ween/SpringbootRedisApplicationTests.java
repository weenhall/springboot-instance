package com.ween;

import com.ween.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class SpringbootRedisApplicationTests {

	@Autowired
	private RedisTemplate<String, Book> redisTemplate;

	@Test
	void save() {
		Book book=new Book();
		book.setId("book-001");
		book.setName("springboot-redis");
		book.setPrice(100.00d);
		redisTemplate.opsForValue().set(book.getId(),book);
	}

	@Test
	void findById(){
		Book book=redisTemplate.opsForValue().get("book-001");
		System.out.println(book.toString());
	}

}
