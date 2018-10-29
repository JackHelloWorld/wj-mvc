package com.wj.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;

import com.wj.common.utils.Tools;

public class ParamRecord extends HashMap<String, Object>{

	private static final long serialVersionUID = -3503239269131185620L;

	public Integer getInt(String key,int def) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isInteger(value))
			return def;
		return Integer.parseInt(value);
	}
	
	public Integer getInt(String key) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isInteger(value))
			return null;
		return Integer.parseInt(value);
	}
	
	public Double getDouble(String key,double def) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isDouble(value))
			return def;
		return Double.parseDouble(value);
	}
	
	public Double getDouble(String key) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isDouble(value))
			return null;
		return Double.parseDouble(value);
	}
	
	public BigDecimal getBigDecimal(String key,BigDecimal def) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isBigDecimal(value))
			return def;
		return new BigDecimal(value);
	}
	

	public BigDecimal getBigDecimal(String key) {
		String value = get(key)==null?"":get(key).toString();
		if(!Tools.isBigDecimal(value))
			return null;
		return new BigDecimal(value);
	}
	
}
