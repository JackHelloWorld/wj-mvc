package com.wj.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.AnnotationUtils;
import com.wj.common.utils.Tools;
import com.wj.dao.mybatis.AuthDao;
import com.wj.dao.mybatis.ListDao;
import com.wj.dao.repository.ResourceRoleRepository;
import com.wj.dao.repository.RoleRepository;
import com.wj.dao.repository.SysResourceRepository;
import com.wj.dao.repository.SysUserRepository;
import com.wj.dao.utils.DaoParam;
import com.wj.dao.utils.PageBean;
import com.wj.dao.utils.PageQuery;
import com.wj.dao.utils.PageUtils;
import com.wj.pojo.sys.ResourceRole;
import com.wj.pojo.sys.Role;
import com.wj.pojo.sys.SysResource;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)
public class RoleService extends BaseService{

	@Resource
	ListDao listDao;

	@Resource
	RoleRepository roleRepository;
	
	@Resource
	AuthDao authDao;
	
	@Resource
	ResourceRoleRepository resourceRoleRepository;

	@Resource
	SysResourceRepository sysResourceRepository;

	@Resource
	SysUserRepository sysUserRepository;

	public ResultTools list(Integer pageNumber, Integer pageSize, Role role) throws Exception {

		AnnotationUtils.paramQuery(role);
		
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {

			@Override
			public List<?> query() {
				List<Map<String, Object>> list = listDao.roleList(role);
				return list;
			}
		},Role.class);
		return ResultTools.SUCCESS(pageBean);
	}

	public ResultTools updateInfo(Long id) {
		Role role = roleRepository.findTop1ByIdAndStatusNot(id,3);
		if(role == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		return ResultTools.SUCCESS(role);
	}

	public ResultTools update(Role role,SysUser user) throws BusinessException {

		Role db = roleRepository.findTop1ByIdAndStatusNot(role.getId(), 3);

		if(db == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		AnnotationUtils.validateEdit(role);

		if(roleRepository.countByNameAndStatusNotAndIdNot(role.getName().trim(),3,db.getId()) > 0)
			ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "角色名称不能重复").throwBusinessException();

		db.setName(role.getName().trim());
		db.setIntro(role.getIntro());
		roleRepository.save(db);
		return ResultTools.SUCCESS();
	}

	public ResultTools delete(Long id,SysUser user) {

		Role db = roleRepository.findTop1ByIdAndStatusNot(id, 3);
		if(db == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		long count = sysUserRepository.countByRoleIdAndStatusNot(db.getId(),2);
		if(count > 0)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "当前角色包含"+count+"个用户,无法删除");

		db.setStatus(3);
		db.setDeleteTime(new Date());
		db.setDeleteUserId(user.getId());
		roleRepository.save(db);
		return ResultTools.SUCCESS();
	}

	public ResultTools save(Role role,SysUser sysUser) throws Exception {

		AnnotationUtils.validateEdit(role);
		
		role.setCreateTime(new Date());
		role.setName(role.getName().trim());
		role.setCreateUser(sysUser.getId());
		role.setStatus(0);
		long countByName = roleRepository.countByNameAndStatusNot(role.getName(),3);
		if(countByName > 0)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "角色名称不能重复");
		roleRepository.save(role);
		return ResultTools.SUCCESS();
	}

	public ResultTools findResource(Long id,SysUser user) {
		
		List<SysResource> sysResources = sysResourceRepository.findAll(new Sort(Direction.ASC, "sort"));
		List<SysResource> resources = authDao.findThisResourceByType(DaoParam.GetParam().put("roleId", id));
		for (SysResource sysResource : sysResources) {
			for (SysResource node : resources) {
				if(node.getId().equals(sysResource.getId())){
					sysResource.setOwn(true);
					break;
				}
			}
		}
		return ResultTools.SUCCESS(initResource(sysResources, 0L));
	}

	public ResultTools resource(Long roleId, String[] ids, SysUser user) throws BusinessException {
		
		Role db = roleRepository.findTop1ByIdAndStatusNot(roleId, 3);
		if(db == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		
		//删除资源配置
		resourceRoleRepository.deleteByRoleId(roleId);
		
		Date actionDate = new Date();
		
		for (String id : ids) {
			if(!Tools.isLong(id))
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源信息选择错误").throwBusinessException();
			
			if(sysResourceRepository.countById(Long.parseLong(id)) == 0)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "资源信息不存在").throwBusinessException();
			
			//储存配置
			ResourceRole resourceRole = new ResourceRole();
			resourceRole.setCreateTime(actionDate);
			resourceRole.setCreateUser(user.getId());
			resourceRole.setResourceId(Long.parseLong(id));
			resourceRole.setRoleId(roleId);
			resourceRoleRepository.save(resourceRole);
			
		}
		
		return ResultTools.SUCCESS();
		
	}

}
