package com.ween.springboothibernate.repository;

import com.ween.springboothibernate.entity.demo.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "book")
@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
