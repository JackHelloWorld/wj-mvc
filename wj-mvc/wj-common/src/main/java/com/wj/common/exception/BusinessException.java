package com.wj.common.exception;

import com.wj.common.config.ResultTools;

/**
 * 业务异常
 * @author jack
 *
 */
public class BusinessException extends Exception{

	private static final long serialVersionUID = -1196851528373507875L;
	
	private ResultTools resultTools;
	
	public BusinessException(ResultTools result){
		super(result.get("msg").toString());
		this.resultTools = result;
	}

	public ResultTools getResultTools() {
		return resultTools;
	}
	

}
