package com.wj.dao.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.github.pagehelper.PageInfo;

public class PageBean extends HashMap<String, Object>{

	private static final long serialVersionUID = 8513601153644778590L;

	public static final PageBean toBean(PageInfo<?> pageInfo) throws Exception{
		PageBean pageBean = new PageBean();
		if(pageInfo == null) return pageBean;
		Field[] fields = pageInfo.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			pageBean.put(field.getName(), field.get(pageInfo));
		}
		return pageBean;
	}



}
