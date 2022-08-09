package com.ween;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SpringbootSecurityApplicationTests {


	@Test
	public void testBCEnCode(){
		String pwd="admin";
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	}

}
