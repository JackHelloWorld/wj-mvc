package com.wj.common.config;

/**
 * 序列号配置
 * @author Jack
 *
 */
public enum SerialNumberNoConfig {

	/**企业编码生成*/
	COMPANY_NO("","COMPANY_NO"),
	
	/**客户编码生成*/
	CUSTOMER_NO("","CUSTOMER_NO");
	
	private String prefix;
	
	private String columnName;
	
	SerialNumberNoConfig(String prefix,String columnName){
		this.columnName = columnName;
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getColumnName() {
		return columnName;
	}
	
	
	
}
