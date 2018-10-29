package com.wj.controller.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.Tools;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.ThreadData;

/**
 * 控制器父类
 * @author LiuJack
 * 
 * @version 1.0
 *
 */
public class BaseController {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	/**
	 * 将map数据分别设置到request作用域
	 * @param request
	 * @param result
	 */
	protected void setAttribute(HttpServletRequest request,Map<String, Object> result){
		for (String key : result.keySet()) {
			request.setAttribute(key, result.get(key));
		}
	}
	
	protected SysUser getUser(){
		return ThreadData.SysUser.get();
	}

	public String getBase64Param(HttpServletRequest request,String paramName){
		String value = request.getParameter(paramName);
		if(Tools.isEmpty(value)) return value;
		try {
			Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method mainMethod= clazz.getMethod("decode", String.class);  
			mainMethod.setAccessible(true);  
			byte[] ret=(byte[])mainMethod.invoke(null, value);
			return new String(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String uploadLocal(MultipartFile img) throws Exception{
		if(img!=null && img.getSize()>0){
			try {
				if(img.getOriginalFilename()==null || img.getOriginalFilename().trim().length()==0)
						throw new BusinessException(ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "文件名称不能为空"));
				String newFileName = Tools.MD5(img.getBytes());
				int i = img.getOriginalFilename().trim().lastIndexOf('.');
				if(i!=-1){
					newFileName = newFileName+img.getOriginalFilename().substring(i, img.getOriginalFilename().trim().length());
				}
				String pathDirs = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/static/file/images"); 
				File dirs = new File(pathDirs);
				File file = new File(pathDirs.concat("/").concat(newFileName));
				if(!dirs.exists()){
					dirs.mkdirs();
				}
				if(!file.exists()){
					OutputStream outputStream = new FileOutputStream(file);
					outputStream.write(img.getBytes());
					outputStream.close();
				}
				return newFileName;
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		}
		return null;
	}


	/**
	 * 获得客户端ip
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isInfoEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

}
