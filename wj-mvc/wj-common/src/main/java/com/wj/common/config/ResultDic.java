package com.wj.common.config;

/**
 * 错误代码字典
 * @author liuJack
 *
 */
public enum ResultDic {

	/**请求成功*/
	SUCCESS(0,"SUCCESS"),

	/**数据传输有误*/
	DATA_WRONG(-100,"数据传输有误"),
	
	/**未登录*/
	NOT_LOGIN(-1,"登录已过期"),
	
	/**无权操作*/
	NOT_PERMISSION(-12,"无权操作"),
	
	/**系统错误*/
	SYS_ERROR(-500,"网络错误");
	
	private Integer code;
	
	private String msg;
	
	ResultDic(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
