package com.wj.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultDic;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.AnnotationUtils;
import com.wj.common.utils.Tools;
import com.wj.dao.repository.SysResourceRepository;
import com.wj.pojo.sys.SysResource;
import com.wj.service.utils.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)
public class ResourceService extends BaseService{

	@Resource
	SysResourceRepository sysResourceRepository;
	
	public ResultTools findMenus() {
		List<SysResource> sysResources = sysResourceRepository.findAll(new Sort(Direction.ASC, "sort"));
		List<SysResource> result = initResource(sysResources, 0L);
		return ResultTools.SUCCESS(result);
	}

	public ResultTools delete(String id) {
		if(!Tools.isLong(id))
			return ResultTools.ERROR(ResultDic.DATA_WRONG);
		SysResource sysResource = sysResourceRepository.getOne(Long.parseLong(id));
		
		if(sysResource == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		sysResourceRepository.delete(sysResource);
		return ResultTools.SUCCESS();
	}

	public ResultTools update(SysResource sysResource) throws BusinessException {
		
		if(sysResource.getId() == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		
		if(sysResourceRepository.countById(sysResource.getId()) == 0)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在");
		
		validateInfo(sysResource);
		sysResourceRepository.save(sysResource);
		return ResultTools.SUCCESS();
		
	}
	
	public ResultTools save(SysResource sysResource) throws BusinessException {
		sysResource.setId(null);
		validateInfo(sysResource);
		sysResourceRepository.save(sysResource);
		return ResultTools.SUCCESS();
	}
	
	private void validateInfo(SysResource sysResource) throws BusinessException{
		
		AnnotationUtils.validateEdit(sysResource);
		
		if(sysResource.getParentId() == null)
			ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "父资源选择错误").throwBusinessException();
		
		if(sysResource.getParentId() != 0){
			if(sysResourceRepository.countById(sysResource.getParentId()) == 0)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "父资源选择错误").throwBusinessException();
		}
		
		if(!sysResource.getType().toString().matches("1|0"))
			ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源类型错误").throwBusinessException();
	
		if(sysResource.getType() == 1){
			if(Tools.isEmpty(sysResource.getClickAction()))
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "当类型为按钮时,必须输入点击事件").throwBusinessException();
		}
	}
}
