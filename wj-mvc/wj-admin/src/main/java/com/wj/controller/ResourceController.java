package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.controller.utils.BaseController;
import com.wj.pojo.sys.SysResource;
import com.wj.service.ResourceService;

@RestController
@RequestMapping("resource")
public class ResourceController extends BaseController{

	@Resource
	ResourceService resourceService;
	
	@PostMapping("find")
	@ValidateAuth("/resource")
	public ResultTools findMenus(){
		return resourceService.findMenus();
	}
	
	@PostMapping("delete")
	public ResultTools delete(String id){
		return resourceService.delete(id);
	}
	
	@PostMapping("update")
	public ResultTools update(SysResource sysResource) throws BusinessException{
		return resourceService.update(sysResource);
	}
	
	@PostMapping("save")
	public ResultTools save(SysResource sysResource) throws BusinessException{
		return resourceService.save(sysResource);
	}
	
}
