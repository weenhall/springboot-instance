package com.ween.asset.repository;

import com.ween.asset.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wen on 2018/8/21
 */
public interface UserRepository extends JpaRepository<Person,String> {

    Person findByUserCode(String userCode);
}
