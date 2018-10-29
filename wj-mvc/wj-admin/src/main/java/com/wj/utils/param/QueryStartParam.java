package com.wj.utils.param;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.wj.common.utils.ParamRecord;
import com.wj.common.utils.Tools;

public class QueryStartParam {

	public static final ParamRecord getStartParam(HttpServletRequest request,String start){
		Enumeration<String> names = request.getParameterNames();
		ParamRecord record = new ParamRecord();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if(name.startsWith(start)){
				String value = request.getParameter(name);
				if(Tools.notNull(value)) value = value.trim();
				record.put(name.substring(start.length()), value);
			}
		}
		return record;
	}
	
	public static final ParamRecord getStartParam(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		ParamRecord record = new ParamRecord();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name);
			if(Tools.notNull(value)) value = value.trim();
			record.put(name, value);
			
		}
		return record;
	}
	
}
