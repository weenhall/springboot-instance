package com.ween.springboothibernate;


import com.ween.springboothibernate.entity.demo.Book;
import com.ween.springboothibernate.entity.demo.Publisher;
import com.ween.springboothibernate.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
class SpringbootHibernateApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Test
	void initEntityData() {
		Publisher publisher=new Publisher();
		publisher.setName("free for publish");
		publisher.setCountry("earth");
		Book book=new Book();
		book.setTitle("learn hibernate");
		book.setAuthor("bob");
		book.setPublisher(publisher);
		book.setCreatedBy("admin");
		book.setCreatedTime(LocalDateTime.now());
		book.setUpdatedBy("admin");
		book.setUpdatedTime(LocalDateTime.now());
		bookRepository.save(book);
	}

}
