package com.wj.common.utils;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class QueryDic extends HashMap<String, String>{

	private static final long serialVersionUID = 6083280934602180193L;
	
	private static final String search = "search_"; 

	private QueryDic(){
		super();
	}
	
	/**
	 * 获取以"search_"开头的参数,并去掉头部
	 * @param request
	 * @return
	 */
	public static final QueryDic getParBySearch(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		QueryDic queryDic = new QueryDic();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			if(name.trim().startsWith(search)){
				queryDic.put(name.trim().substring(search.length()), request.getParameter(name));
			}
		}
		return queryDic;
	}
	
	/**
	 * 获取以指定开头的参数,并去掉头部
	 * @param request
	 * @return
	 */
	public static final QueryDic getParBySearchName(String start){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		QueryDic queryDic = new QueryDic();
		if(Tools.isNull(start)){
			throw new RuntimeException("开头信息不能为空");
		}
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String name = (String) enumeration.nextElement();
			if(name.trim().startsWith(start.trim())){
				queryDic.put(name.trim().substring(start.trim().length()), request.getParameter(name));
			}
		}
		return queryDic;
	}
	
	public static final QueryDic getQueryDic(){
		return new QueryDic();
	}
	
	public static final QueryDic setQueryDic(String name,String value){
		QueryDic dic = new QueryDic();
		dic.put(name, value);
		return dic;
	}
	
	
	
}
