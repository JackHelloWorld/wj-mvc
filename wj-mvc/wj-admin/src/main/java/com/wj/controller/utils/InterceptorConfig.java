package com.wj.controller.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wj.common.interceptor.SysInterceptor;

@Configuration
@SuppressWarnings("deprecation")
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public SysInterceptor getSysInterceptor(){
		return new SysInterceptor();
	}
	
	@Bean
	public UserInterceptor getUserInterceptor(){
		return new UserInterceptor();
	}
	
	@Bean
	public AuthInterceptor getAuthInterceptor(){
		return new AuthInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getSysInterceptor());
		registry.addInterceptor(getUserInterceptor()).excludePathPatterns("/error");
		registry.addInterceptor(getAuthInterceptor()).excludePathPatterns("/error");
		super.addInterceptors(registry);
	}

}
