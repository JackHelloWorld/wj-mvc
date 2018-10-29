package com.wj.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wj.common.config.Const;
import com.wj.common.config.ResultTools;
import com.wj.dao.mybatis.AuthDao;
import com.wj.dao.repository.SysResourceRepository;
import com.wj.dao.utils.DaoParam;
import com.wj.pojo.sys.SysResource;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.BaseService;

@Service
public class HomeService extends BaseService{

	@Resource
	SysResourceRepository sysResourceRepository;
	
	@Resource
	Const constData;
	
	@Resource
	AuthDao authDao;
	
	public ResultTools findMenus(SysUser user) throws Exception {

		List<SysResource> sysResources;
		if(user.getIsAdmin()){
			sysResources = sysResourceRepository.findByTypeOrderBySortAsc(0);
		}else{
			sysResources = authDao.findThisResourceByType(
					DaoParam.GetParam()
					.put("type", 0)
					.put("roleId", user.getRoleId()));
		}
		
		return ResultTools.SUCCESS(initResource(sysResources, 0L));
	
	}

	public ResultTools findActions(Long id,SysUser user) throws Exception {

		List<SysResource> sysResources;
		if(user.getIsAdmin()){
			sysResources = sysResourceRepository.findByTypeAndParentIdOrderBySortAsc(1,id);
		}else{
			sysResources = authDao.findThisResourceByType(
					DaoParam.GetParam()
					.put("type", 1)
					.put("parentId", id)
					.put("roleId", user.getRoleId()));
		}
		
		return ResultTools.SUCCESS(sysResources);
		
	
	}

	public ResultTools searchMenus(SysUser user) throws Exception {
		List<SysResource> sysResources;
		if(user.getIsAdmin()){
			sysResources = sysResourceRepository.findByTypeOrderBySortAsc(0);
		}else{
			sysResources = authDao.findThisResourceByType(
					DaoParam.GetParam()
					.put("type", 0)
					.put("roleId", user.getRoleId()));
		}
		
		return ResultTools.SUCCESS(sysResources);
		
	}
}
