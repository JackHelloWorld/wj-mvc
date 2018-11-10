package com.wj.controller.utils;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.wj.common.config.Const;
import com.wj.pojo.sys.SysUser;

@Component
@ServerEndpoint(value = "/websocket",configurator=GetHttpSessionConfigurator.class)
public class AdminWebSocket implements Serializable{

	private static final long serialVersionUID = 3107203343599588111L;

	@Resource
	RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	Const constData;
	
    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
    	HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
    	SysUser user = (SysUser) httpSession.getAttribute(constData.LOGIN_SESSION_USER);
    	if(user != null){
    		
    	}
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
    	
    }

}
