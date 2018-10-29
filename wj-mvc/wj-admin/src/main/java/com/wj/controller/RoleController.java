package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.config.ResultDic;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.Tools;
import com.wj.controller.utils.BaseController;
import com.wj.pojo.sys.Role;
import com.wj.service.RoleService;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController{

	@Resource
	RoleService roleService;
	
	@PostMapping("list")
	@ValidateAuth("/role")
	public ResultTools list(@RequestParam(value="pageSize",defaultValue="10")Integer pageSize,
			@RequestParam(value="pageNumber",defaultValue="1")Integer pageNumber,Role role) throws Exception{

		return roleService.list(pageNumber,pageSize,role);
	}
	
	@ValidateAuth("/role/update")
	@PostMapping("update/info")
	public ResultTools updateInfo(@RequestParam(value="id",defaultValue="")String id){
		if(!Tools.isLong(id))
			return ResultTools.ERROR(ResultDic.DATA_WRONG);
		return roleService.updateInfo(Long.parseLong(id));
	}
	
	@PostMapping("save")
	public ResultTools save(Role role) throws Exception{
		return roleService.save(role,getUser());
	}
	
	@PostMapping("delete")
	public ResultTools delete(@RequestParam(value="id",defaultValue="")String id){
		if(!Tools.isLong(id))
			return ResultTools.ERROR(ResultDic.DATA_WRONG);
		return roleService.delete(Long.parseLong(id),getUser());
	}
	
	@PostMapping("update")
	public ResultTools update(Role role) throws BusinessException{
		return roleService.update(role,getUser());
	}
	
	@ValidateAuth("/role/resource")
	@PostMapping("find/resource")
	public ResultTools findResource(@RequestParam(value="id",defaultValue="0")Long id){
		return roleService.findResource(id,getUser());
	}
	
	@PostMapping("resource")
	public ResultTools resource(@RequestParam(value="id",defaultValue="0")Long id,@RequestParam(value="ids",defaultValue="")String ids) throws BusinessException{
		String[] ids1 = ids.split(",");
		return roleService.resource(id,ids1,getUser());
	}
	
}
