package com.wj.controller.utils;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wj.common.annotation.ValidateIgnoreLogin;
import com.wj.common.config.Const;
import com.wj.common.config.ResultDic;
import com.wj.common.config.ResultTools;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.ThreadData;

public class UserInterceptor implements HandlerInterceptor{
	
	@Resource
	Const constData;
	
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception exception)	throws Exception {
		ThreadData.SysUser.remove();
	}

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute(constData.LOGIN_SESSION_USER);
		if(user == null){
			
			if(handler instanceof HandlerMethod){
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Method method = handlerMethod.getMethod();
				
				ValidateIgnoreLogin validateIgnoreLogin = method.getAnnotation(ValidateIgnoreLogin.class);
				if(validateIgnoreLogin != null)
					return true;
			}
			ResultTools.ERROR(ResultDic.NOT_LOGIN).throwBusinessException();
		
		}
		ThreadData.SysUser.set(user);
		return true;
	}

}
