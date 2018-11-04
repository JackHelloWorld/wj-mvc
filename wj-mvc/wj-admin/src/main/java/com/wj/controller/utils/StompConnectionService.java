package com.wj.controller.utils;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wj.common.config.Const;
import com.wj.common.utils.Tools;
import com.wj.pojo.sys.SysUser;
import com.wj.service.UserPrincipalVo;

/**
 * stomp连接处理类
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor=Exception.class)
public class StompConnectionService extends ChannelInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(StompConnectionService.class);

	@Resource
	RedisTemplate<String, Object> redisTemplate;

	@Resource
	Const constData;

	@Override
	@SuppressWarnings("unchecked")
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		System.out.println(sha.getCommand());
		// ignore non-STOMP messages like heartbeat messages
		if(sha.getCommand() == null) {
			return;
		}
		

		Map<String, Object> simpSessionAttributes = (Map<String, Object>) sha.getHeader("simpSessionAttributes");
		SysUser user = (SysUser) simpSessionAttributes.get(constData.LOGIN_SESSION_USER);
		String sessionId = (String) simpSessionAttributes.get("HTTP.SESSION.ID");
		if(user == null)//用户未登录
			return;
		String userId = user.getId().toString();

		try {
			String userInfo = Tools.MD5(userId, "user_info");
			UserPrincipalVo userPrincipalVo = new UserPrincipalVo(userInfo.concat(sessionId));
			sha.setUser(userPrincipalVo);
			redisTemplate.opsForHash().put(userInfo, sessionId, userPrincipalVo);
			System.out.println(redisTemplate.opsForHash().get(userInfo, sessionId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//判断客户端的连接状态
		switch(sha.getCommand()) {
		case CONNECT:
			connect(sessionId, userId,sha);
			break;
		case CONNECTED:
			break;
		case DISCONNECT:
			disconnect(sessionId, userId, sha);
			break;
		default:
			break;
		}
	}

	//连接成功
	private void connect(String sessionId,String userId,StompHeaderAccessor sha){
		try {
			String userInfo = Tools.MD5(userId, "user_info");
			UserPrincipalVo userPrincipalVo = new UserPrincipalVo(userInfo.concat(sessionId));
			sha.setUser(userPrincipalVo);
			redisTemplate.opsForHash().put(userInfo, sessionId, userPrincipalVo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//断开连接
	private void disconnect(String sessionId,String userId, StompHeaderAccessor sha){
		try {
			String userInfo = Tools.MD5(userId, "user_info");
			if(redisTemplate.opsForHash().get(userInfo, sessionId) != null){
				redisTemplate.opsForHash().delete(userInfo, sessionId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
