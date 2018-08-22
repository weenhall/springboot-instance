package com.ween.asset.service;

import com.ween.asset.entity.Person;
import com.ween.asset.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wen on 2018/8/21
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Person findPersonByUserCode(String userCode){
        return userRepository.findByUserCode(userCode);
    }
}
