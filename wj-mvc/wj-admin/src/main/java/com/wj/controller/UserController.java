package com.wj.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.annotation.ValidateIgnoreLogin;
import com.wj.common.config.Const;
import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.utils.Tools;
import com.wj.controller.utils.BaseController;
import com.wj.controller.utils.RandomImageGenerator;
import com.wj.dto.SysLoginDto;
import com.wj.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

	@Resource
	UserService userService;
	
	@Resource
	Const constData;
	
	@ValidateIgnoreLogin
	@PostMapping("/login")
	public ResultTools login(SysLoginDto sysLoginDto,HttpServletRequest request) throws Exception{
		String rCode = (String) request.getSession().getAttribute("code");
		if(Tools.isEmpty(sysLoginDto.getCode()) || !sysLoginDto.getCode().trim().equalsIgnoreCase(rCode))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "验证码输入错误");
		return userService.login(sysLoginDto,getIpAddress(request));
	}
	
	@ValidateIgnoreLogin
	@GetMapping("/login/code")
	public void loginCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String code = userService.generateCode();
		request.getSession().setAttribute("code", code);
		RandomImageGenerator.render(code, response.getOutputStream(), 95, 41);
	}

	@ValidateAuth(validate=false)
	@PostMapping("/get/info")
	public ResultTools getInfo(){
		return ResultTools.SUCCESS(getUser());
	}
	
	@ValidateAuth(validate=false)
	@PostMapping("update/password")
	public ResultTools updatePwd(@RequestParam(value="oldpass",defaultValue="")String oldpass,
			@RequestParam(value="newpass",defaultValue="")String newpass) throws Exception{
		return userService.updatePwd(oldpass,newpass,getUser());
	}
	
	@ValidateIgnoreLogin
	@ValidateAuth(validate=false)
	@PostMapping("/exit")
	public ResultTools exit(HttpServletRequest request){
		request.getSession().removeAttribute(constData.LOGIN_SESSION_USER);
		return ResultTools.SUCCESS();
	}
	
}
