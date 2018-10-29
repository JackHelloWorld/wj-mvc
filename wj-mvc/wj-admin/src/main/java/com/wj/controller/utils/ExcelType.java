package com.wj.controller.utils;

public enum ExcelType {
	XLS(1),
	XLSX(2);
	private int type;
	private ExcelType(int type){
		this.type=type;
	}
	public Integer getType(){
		return type;
	}
}
