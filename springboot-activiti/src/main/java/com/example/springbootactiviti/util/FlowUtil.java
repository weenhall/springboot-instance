package com.example.springbootactiviti.util;

import org.springframework.stereotype.Component;

import java.util.*;

@Component("flowUtil")
public class FlowUtil {

	public List<String> stringToList(String val) {
		System.out.println(val);
//		return Arrays.asList("zhangshan","lisi");
		return Arrays.asList("bob","alice");
	}
}
