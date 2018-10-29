package com.wj.common.utils;

/**
 * 时间格式枚举
 * @author LiuJack
 *
 */
public enum DatePattern {
	/**
	 * yyyy-MM-dd
	 */
	YYYYMMDD("yyyy-MM-dd"),
	
	/**
	 * yyyy/MM/dd
	 */
	YYYYMMDDX("yyyy/MM/dd"),
	
	/**
	 * yyyyMMdd
	 */
	YYYYMMDDD("yyyyMMdd"),
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss"),
	
	/**
	 * yyyyMMddHHmmss
	 */
	YYYYMMDDHHMMSSS("yyyyMMddHHmmss"),
	
	/**
	 * yyyy-MM
	 */
	YYYYMM("yyyy-MM")
	;
	
	private String value;
	
	private DatePattern(String value) {
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
