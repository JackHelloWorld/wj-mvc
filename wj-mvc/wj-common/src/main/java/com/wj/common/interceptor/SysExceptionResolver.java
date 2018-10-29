package com.wj.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wj.common.config.ResultDic;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;

@Configuration
public class SysExceptionResolver implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {

		if(exception != null){
			//打印错误信息
			exception.printStackTrace();
			ResultTools resultTools = null;
			if(exception instanceof BusinessException)
				resultTools = ((BusinessException)exception).getResultTools();
			else 
				resultTools = ResultTools.ERROR(ResultDic.SYS_ERROR);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=utf-8");
			try {
				String json = JSONObject.toJSONString(resultTools);
				response.getWriter().write(json);
				response.getWriter().flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView();
		}
		return new ModelAndView();
	}
}
