package com.wj.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLoginDto implements Serializable{

	private static final long serialVersionUID = 7714459133580727996L;

	/**验证码*/
	private String code;

	/**登录名*/
	private String login_name;
	
	/**密码*/
	private String password;
	
}
