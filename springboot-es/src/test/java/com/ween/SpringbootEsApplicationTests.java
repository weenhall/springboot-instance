package com.ween;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class SpringbootEsApplicationTests {

	@Test
	void contextLoads() {
		String str="<font color='red'>11</font>";
		System.out.println(str.replaceAll("<[^>]*>", ""));;
	}

}
