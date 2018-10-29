package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.config.ResultTools;
import com.wj.controller.utils.BaseController;
import com.wj.pojo.sys.SysUser;
import com.wj.service.UserService;

@RestController
@RequestMapping("webuser")
public class WebUserController extends BaseController{

	@Resource
	UserService userSverive;
	
	@PostMapping("list")
	@ValidateAuth("/webuser")
	public ResultTools list(@RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,SysUser user) throws Exception{

		return userSverive.list(pageNumber,pageSize,user);
	}
	
	@ValidateAuth("/webuser")
	@PostMapping("find/roles")
	public ResultTools findRoles(){
		return userSverive.findRoles(getUser());
	}
	
	@PostMapping("save")
	public ResultTools save(SysUser sysUser) throws Exception{
		return userSverive.save(sysUser,getUser());
	}
	
	@PostMapping("update")
	public ResultTools update(SysUser sysUser) throws Exception{
		return userSverive.update(sysUser,getUser());
	}
	
	@ValidateAuth("/webuser/update")
	@PostMapping("update/info")
	public ResultTools updateInfo(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userSverive.updateInfo(id,getUser());
	}
	
	@PostMapping("success")
	public ResultTools success(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userSverive.success(id,getUser());
	}
	
	@PostMapping("block")
	public ResultTools block(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userSverive.block(id,getUser());
	}
	
	@PostMapping("delete")
	public ResultTools delete(@RequestParam(value="id",defaultValue="0") Long id) throws Exception{
		return userSverive.delete(id,getUser());
	}
	
}
