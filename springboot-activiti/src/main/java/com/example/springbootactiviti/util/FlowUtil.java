package com.example.springbootactiviti.util;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component("flowUtil")
public class FlowUtil {

	public List<String> stringToList(String val){
		String [] arr=val.split(",");
		return Arrays.asList(arr);
	}
}
