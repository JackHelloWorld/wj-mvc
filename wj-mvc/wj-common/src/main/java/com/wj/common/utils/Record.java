package com.wj.common.utils;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Record extends HashMap<String, Object>{

	private static final long serialVersionUID = -3712396864942166642L;
	
	public <T> T getT(String key){
		Object value = get(key);
		return (T)value;
	}
	
}
