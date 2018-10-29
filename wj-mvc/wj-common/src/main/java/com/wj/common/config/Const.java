package com.wj.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置信息
 * @author liuJack
 *
 */
@Component
public class Const {

	/**登录用户session名称*/
	@Value("${user.login.session.user}")
	public String LOGIN_SESSION_USER;
	
	//ftp 信息-----------------
	/**ftp登录ip*/
	@Value("${user.ftp.ip}")
	public String FTP_IP;
	
	/**ftp登录端口*/
	@Value("${user.ftp.prot}")
	public Integer FTP_PROT;
	
	/**ftp登录名*/
	@Value("${user.ftp.login.name}")
	public String FTP_LOGIN_NAME;
	
	/**ftp登录密码*/
	@Value("${user.ftp.login.password}")
	public String FTP_PASSWORD;
	
	/**ftp图片上传,读取地址*/
	@Value("${user.ftp.img.path}")
	public String ftpImgPath;
	
	/**ftp文件上传,读取地址*/
	@Value("${user.ftp.file.path}")
	public String ftpFilePath;
	
	//分页信息---------------------------
	@Value("${user.page.size}")
	public Integer pageSize;
	
	@Value("${user.page.number}")
	public Integer pageNumber;
	
	@Value("${user.system.default.password}")
	public String defaultPassword;
	
}
