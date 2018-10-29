package com.wj.controller.utils;

import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.annotation.ValidateIgnoreLogin;
import com.wj.common.config.ResultDic;
import com.wj.common.config.ResultTools;
import com.wj.pojo.sys.SysUser;
import com.wj.service.AuthService;
import com.wj.service.utils.ThreadData;

public class AuthInterceptor implements HandlerInterceptor{

	@Resource
	AuthService authService;

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		if(handler instanceof HandlerMethod){
			
			String url = request.getRequestURI();
			
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			
			ValidateIgnoreLogin validateIgnoreLogin = method.getAnnotation(ValidateIgnoreLogin.class);
			if(validateIgnoreLogin != null)
				return true;
			
			ValidateAuth validateConfig = method.getAnnotation(ValidateAuth.class);
			if(validateConfig != null){
				if(!validateConfig.validate()) 
					return true;
				url = validateConfig.value();
			}
			SysUser sysUser = ThreadData.SysUser.get();
			boolean result = authService.validate(sysUser,url);
			if(!result)
				ResultTools.ERROR(ResultDic.NOT_PERMISSION).throwBusinessException();
		}
		return true;
	}

}
