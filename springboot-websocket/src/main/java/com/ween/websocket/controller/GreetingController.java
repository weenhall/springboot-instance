package com.ween.websocket.controller;

import com.ween.websocket.model.Greeting;
import com.ween.websocket.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(Message message) throws Exception{
		Thread.sleep(1000);
		return new Greeting("hello,"+ HtmlUtils.htmlEscape(message.getText()) + "!");
	}
}
