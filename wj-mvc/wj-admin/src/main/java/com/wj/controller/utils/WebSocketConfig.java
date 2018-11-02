package com.wj.controller.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@SuppressWarnings("rawtypes")
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker 
public class WebSocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(adminHandler(), "/adminHandler");
	}
	
	@Bean
    public WebSocketHandler adminHandler() {
        return new AdminWebSocketHandler();
    }

	@Override
	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		  registry.addEndpoint("/adminHandler").withSockJS();
	}

}
