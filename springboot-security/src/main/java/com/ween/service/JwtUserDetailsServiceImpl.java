package com.ween.service;

import com.ween.entity.User;
import com.ween.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional=repository.findByUsername(username);
		if(userOptional.isPresent()){
			User currentUser=userOptional.get();
			if(currentUser.getUsername().equals(username)){
				return new User(currentUser.getId(),currentUser.getUsername(),currentUser.getPassword());
			}
		}
		log.info("username:"+username+"not found");
		throw  new UsernameNotFoundException("username:"+username+"not found");
	}
}
