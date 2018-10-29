package com.wj.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateIgnoreLogin;
import com.wj.common.config.ResultTools;
import com.wj.controller.utils.BaseController;

@RestController
public class SysController extends BaseController{
	
	@GetMapping("/")
	@ValidateIgnoreLogin
	public ResultTools index() {
		return ResultTools.SUCCESS("Service running...");
	}
	
}
