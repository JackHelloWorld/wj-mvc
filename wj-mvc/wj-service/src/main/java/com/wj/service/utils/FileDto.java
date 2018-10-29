package com.wj.service.utils;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto implements Serializable{

	private static final long serialVersionUID = 7420882721598219237L;
	
	@JsonIgnore
	private byte[] bytes;
	
	/**文件类型*/
	private String content_type;
	
	/**上传时间*/
	private Date upload_time;
	
	/**文件原名*/
	private String old_name;
	
	/**储存名称*/
	private String name;
	
	/**存储路径*/
	private String path;
	
	/**完整路径*/
	private String full_path;
	
	/**存储服务器路径*/
	private String service_path;
	
	public String getSuffix(){
		if(old_name != null){
			return old_name.substring(old_name.lastIndexOf('.'));
		}
		return null;
	}

}
