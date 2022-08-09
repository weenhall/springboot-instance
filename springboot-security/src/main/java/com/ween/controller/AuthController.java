package com.ween.controller;

import com.ween.entity.User;
import com.ween.jwt.JwtTokenUtil;
import com.ween.model.vo.AuthVO;
import com.ween.repository.UserRepository;
import com.ween.service.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init(){
		User user= User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin123"))
				.build();
		userRepository.save(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthVO authVO){
		authenticate(authVO.getUsername(),authVO.getPassword());
		User currentUser=jwtUserDetailsService.loadUserByUsername(authVO.getUsername());
		String accessToken=jwtTokenUtil.generateAccessToken(currentUser);
		authVO.setAccessToken(accessToken);
		return ResponseEntity.ok(authVO);
	}

	private void authenticate(String username,String password){
		try{
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		}catch (BadCredentialsException e){
			throw new BadCredentialsException("无效的用户名或密码",e);
		}
	}
}
