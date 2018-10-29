package com.wj.common.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wj.common.config.Const;

/**
 * ftp上传工具类
 * @createTime 2018年1月10日
 * @author Jack
 *
 */
@Component("ftpUtils")
public class FtpUtils {
	
	 private static Logger logger = LoggerFactory.getLogger(FtpUtils.class);  
	 
	 @Resource
	 Const constData;

	/** 
	 * Description: 向FTP服务器上传文件 
	 * @param filePath FTP服务器文件存放路径
	 * @param filename 上传到FTP服务器上的文件名 
	 * @param input 输入流 
	 * @return 成功返回true，否则返回false
	 */  
	public boolean uploadFile(String filename, InputStream input) {
		
		String filePath = "";
		
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(constData.FTP_IP,constData.FTP_PROT);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(constData.FTP_LOGIN_NAME, constData.FTP_PASSWORD);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("未连接到FTP，用户名或密码错误。");
				return result;
			}else{
				logger.debug("链接成功");
			}
			//切换到上传目录
			if (!ftp.changeWorkingDirectory("/"+filePath)) {
				//如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = "/";
				for (String dir : dirs) {
					if (null == dir || "".equals(dir)) continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			ftp.setBufferSize(1024);
			ftp.setControlEncoding("GBK");
			ftp.enterLocalPassiveMode();
			//设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			logger.error("ftp操作错误", e);
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.error("ftp操作错误", ioe);
				}
			}
		}
		return result;
	}
	
}