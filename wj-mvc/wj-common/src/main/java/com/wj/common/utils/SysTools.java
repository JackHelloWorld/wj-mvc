package com.wj.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 系统工具类
 * @author liuJack
 *
 */
public class SysTools {
	
	/**
	 * 获得HttpServletRequest对象
	 * @return HttpServletRequest对象
	 */
	public static final HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获得HttpSession对象
	 * @return HttpSession对象
	 */
	public static final HttpSession getSession(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
}
