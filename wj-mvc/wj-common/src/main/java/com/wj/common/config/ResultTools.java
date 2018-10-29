package com.wj.common.config;

import java.util.HashMap;

import com.wj.common.exception.BusinessException;

/**
 * 结果处理工具
 * @author liuJack
 *
 */
public final class ResultTools extends HashMap<String, Object>{

	private static final long serialVersionUID = 2012087081741204962L;
	
	/**结果提示信息取值字符*/
	private static final String msg = "msg";
	
	/**结果编码取值字符--详情见ResultDic.class*/
	private static final String code = "code";
	
	/**结果附带数据取值字符*/
	private static final String data = "data";
	
	private ResultTools(){super();}
	
	/**
	 * 操作成功,并返回数据
	 * @param data 需要返回的数据
	 * @return
	 */
	public static final ResultTools SUCCESS(Object data){
		ResultTools tools = new ResultTools();
		tools.put(code, ResultDic.SUCCESS.getCode());
		tools.put(msg, ResultDic.SUCCESS.getMsg());
		tools.put(ResultTools.data, data);
		return tools;
	}

	/**
	 * 操作成功,无返回数据
	 * @return
	 */
	public static final ResultTools SUCCESS(){
		ResultTools tools = new ResultTools();
		tools.put(code, ResultDic.SUCCESS.getCode());
		tools.put(msg, ResultDic.SUCCESS.getMsg());
		return tools;
	}
	

	/**
	 * 操作错误,并返回数据
	 * @param dic 错误字典
	 * @param error 需要返回的数据
	 * @return
	 */
	public static final ResultTools ERROR(ResultDic dic,Object error){
		ResultTools tools = new ResultTools();
		tools.put(code, dic.getCode());
		tools.put(msg, dic.getMsg());
		tools.put(data, error);
		return tools;
	}

	/**
	 * 操作错误,无返回数据
	 * @param dic 错误字典
	 * @return
	 */
	public static final ResultTools ERROR(ResultDic dic){
		ResultTools tools = new ResultTools();
		tools.put(code, dic.getCode());
		tools.put(msg, dic.getMsg());
		return tools;
	}
	

	/**
	 * 自定义操作错误信息,并返回数据
	 * @param code 错误编码
	 * @param msg 错误信息
	 * @param data 返回的数据
	 * @return
	 */
	public static final ResultTools DIY_ERROR(ResultCode code,String msg,Object data){
		ResultTools tools = new ResultTools();
		tools.put(ResultTools.code, code.getCode());
		tools.put(ResultTools.msg, msg);
		tools.put(ResultTools.data, data);
		return tools;
	}
	
	/**
	 * 自定义操作错误信息,无返回数据
	 * @param code 错误编码
	 * @param msg 错误信息
	 * @return
	 */
	public static final ResultTools DIY_ERROR(ResultCode code,String msg){
		ResultTools tools = new ResultTools();
		tools.put(ResultTools.code, code.getCode());
		tools.put(ResultTools.msg, msg);
		return tools;
	}

	public static ResultTools ERROR(Integer code, String msg) {
		ResultTools tools = new ResultTools();
		tools.put(ResultTools.code, code);
		tools.put(ResultTools.msg, msg);
		return tools;
	}
	
	/**
	 * 保存结果异常
	 * @throws BusinessException
	 */
	public void throwBusinessException() throws BusinessException{
		throw new BusinessException(this);
	}
	

}

