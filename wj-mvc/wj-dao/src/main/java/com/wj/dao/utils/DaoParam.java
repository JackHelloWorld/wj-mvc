package com.wj.dao.utils;

import java.util.HashMap;

import com.wj.common.utils.Tools;

/**
 * dao查询参数设置
 * @author liuji
 *
 */
public class DaoParam extends HashMap<String, Object>{

	private static final long serialVersionUID = -568930056596417858L;
	
	private DaoParam(){
		super();
	}
	
	public static final DaoParam GetParam(){
		return new DaoParam();
	}
	
	public DaoParam put(String key,Object value){
		if(value instanceof String && Tools.notNull(value)){
			super.put(key, value.toString().trim());
		}else{
			super.put(key, value);
		}
		return this;
	}

}
