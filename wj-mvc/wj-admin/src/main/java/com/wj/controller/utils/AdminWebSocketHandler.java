package com.wj.controller.utils;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class AdminWebSocketHandler extends TextWebSocketHandler {

	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
       System.out.println("==============");
    }
	
}
