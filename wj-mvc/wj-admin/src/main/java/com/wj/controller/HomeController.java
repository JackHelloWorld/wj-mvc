package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.config.ResultTools;
import com.wj.common.utils.Tools;
import com.wj.controller.utils.BaseController;
import com.wj.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController extends BaseController{

	@Resource
	HomeService homeService;
	
	@ValidateAuth(validate=false)
	@PostMapping("/find/menus")
	public ResultTools findMenus() throws Exception{
		return homeService.findMenus(getUser());
	}
	
	@ValidateAuth(validate=false)
	@PostMapping("/search/menus")
	public ResultTools searchMenus() throws Exception{
		return homeService.searchMenus(getUser());
	}
	
	@ValidateAuth(validate=false)
	@PostMapping("/find/action")
	public ResultTools findActions(@RequestParam(value="id",defaultValue="0")String id) throws Exception{
		if(!Tools.isLong(id))
			return ResultTools.SUCCESS();
		return homeService.findActions(Long.parseLong(id),getUser());
	}
}
