package com.wj.dao.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PageUtils {

	public static final PageBean query(Integer pageNumber,Integer pageSize,PageQuery pageQuery) throws Exception{
		
		PageHelper.startPage(pageNumber, pageSize);
		List<?> list = pageQuery.query();
		PageInfo<?> pageInfo = new PageInfo<>(list);
		return PageBean.toBean(pageInfo);
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public static final <T extends Serializable> PageBean query(Integer pageNumber,Integer pageSize,PageQuery pageQuery,Class<T> clazz) throws Exception{
		
		PageHelper.startPage(pageNumber, pageSize);
		List<?> list = pageQuery.query();
		PageInfo pageInfo = new PageInfo<>(list);
		List<T> resultList = new ArrayList<>();
		for (Object obj : pageInfo.getList()) {
			Map<String, Object> map = (Map<String, Object>) obj;
			T temp = mapToEntity(map, clazz);
			resultList.add(temp);
		}
		pageInfo.setList(resultList);
		return PageBean.toBean(pageInfo);
	}
	
	public static final <T extends Serializable> T mapToEntity(Map<String, Object> map,Class<T> clazz) throws Exception{
		T temp = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			if(map.containsKey(field.getName())){
				field.set(temp, map.get(field.getName()));
			}else{
				Column column = field.getAnnotation(Column.class);
				if(column != null && map.containsKey(column.name())){
					field.set(temp, map.get(column.name()));
				}
			}
		}
		return temp;
	}
	
}
