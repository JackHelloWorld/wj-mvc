package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

	@Resource
    SimpMessagingTemplate messagingTemplate;
	
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	@Scheduled(cron = "0/5 * * * * *")
	@SendToUser(destinations="/queue/message")
	public void sendMessage(){
		System.out.println("-----------");
		messagingTemplate.convertAndSendToUser("1A2294153CB43AA4CEE6600D221E2CBFb8b0b9e8-304a-480c-a3b1-0589c85c8a0b", "/queue/message", "新消息");
	}
	
	@MessageExceptionHandler
	public void handleExceptions(Exception e,String json) {
		System.out.println("send message error");
	}
	
}
