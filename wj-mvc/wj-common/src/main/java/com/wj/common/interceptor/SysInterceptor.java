package com.wj.common.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统拦截器
 * @author Jack
 *
 */
public class SysInterceptor implements HandlerInterceptor {
	
	private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	/**
	 * 最后执行！！！
	 */
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception exception)	throws Exception {
		
	}

	/**
	 * Action执行之后，生成视图之前执行！！
	 */
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("utf-8");
		if(handler instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod)handler;
			StringBuilder sb = new StringBuilder("\nRequest action report -------- ").append(sdf.get().format(new Date())).append(" ------------------------------\n");
			String className = (method.getBean().getClass().getName());
			sb.append("Controller  : ").append(method.getBean().getClass().getName()).append(".(").append(className.trim().substring(className.lastIndexOf('.')+1, className.length())).append(".java:1)");
			sb.append("\nMethod : ").append(method);
			sb.append("\nParameters : ");
			Enumeration<String> names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				sb.append(name).append(" = ").append(request.getParameter(name)).append("   ");
			}
			
			sb.append("\nheaders : ");
			Enumeration<String> headers = request.getHeaderNames();
			while (headers.hasMoreElements()) {
				String header = headers.nextElement();
				sb.append(header).append(" = ").append(request.getHeader(header)).append("   ");
			}
			sb.append("\nRequest url  :\t").append(request.getRequestURI());
			sb.append("\n--------------------------------------------------------------------------------\n");
			System.out.print(sb.toString());
		}
		return true;
	}

}

