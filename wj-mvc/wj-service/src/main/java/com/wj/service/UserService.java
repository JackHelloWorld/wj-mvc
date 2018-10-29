package com.wj.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.AnnotationUtils;
import com.wj.common.utils.SysTools;
import com.wj.common.utils.Tools;
import com.wj.dao.mybatis.ListDao;
import com.wj.dao.repository.RoleRepository;
import com.wj.dao.repository.SysUserLoginLogRepository;
import com.wj.dao.repository.SysUserRepository;
import com.wj.dao.utils.PageBean;
import com.wj.dao.utils.PageQuery;
import com.wj.dao.utils.PageUtils;
import com.wj.dto.SysLoginDto;
import com.wj.pojo.sys.Role;
import com.wj.pojo.sys.SysUser;
import com.wj.pojo.sys.SysUserLoginLog;
import com.wj.service.utils.BaseService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserService extends BaseService{
	
	@Resource
	SysUserRepository sysUserRepository;
	
	@Resource
	RoleRepository roleRepository;
	
	@Resource
	SysUserLoginLogRepository sysUserLoginLogRepository;
	
	@Resource
	ListDao listDao;

	public ResultTools login(SysLoginDto sysLoginDto,String ip) throws Exception {
		
		if(Tools.isEmpty(sysLoginDto.getLogin_name()))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "请输入登录名");
		
		if(Tools.isEmpty(sysLoginDto.getPassword()))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "请输入登录密码");
		
		SysUser sysUser = sysUserRepository.findTop1ByLoginNameAndStatusNot(sysLoginDto.getLogin_name(),2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "登录用户不存在");
		
		switch (sysUser.getStatus()) {
		case 1:
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "用户已停用,请联系管理员");

		default:
			break;
		}
		
		String pass = Tools.MD5(sysLoginDto.getPassword(), sysUser.getLoginEncryption());
		
		if(!sysUser.getLoginPwd().equals(pass))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "用户名或密码错误");
		
		SysTools.getSession().setAttribute(constData.LOGIN_SESSION_USER, sysUser);
		
		SysUserLoginLog sysUserLoginLog = new SysUserLoginLog();
		sysUserLoginLog.setLoginIp(ip);
		Date date = new Date();
		sysUser.setLastLoginTime(date);
		sysUserLoginLog.setLoginTime(date);
		sysUserLoginLog.setUserId(sysUser.getId());
		sysUserLoginLogRepository.save(sysUserLoginLog);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}
	

	/**
	 * 生成验证码
	 * @Title 
	 * @Desc 
	 * @return
	 */
	public String generateCode() {
		return generateEncry(4);
	}


	public ResultTools list(Integer pageNumber, Integer pageSize, SysUser user) throws Exception {

		AnnotationUtils.paramQuery(user);
		
		PageBean pageBean = PageUtils.query(pageNumber, pageSize, new PageQuery() {
			
			@Override
			public List<?> query() {
				List<Map<String, Object>> list = listDao.sysUserList(user);
				return list;
			}
		},SysUser.class);
		return ResultTools.SUCCESS(pageBean);
	}


	public ResultTools findRoles(SysUser user) {
		List<Role> roles = roleRepository.findByStatus(0);
		return ResultTools.SUCCESS(roles);
	}

	public ResultTools save(SysUser sysUser, SysUser user) throws Exception {
		sysUser.setId(null);
		sysUser.setIsAdmin(false);
		validateInfo(sysUser);
		sysUser.setCreateTime(new Date());
		sysUser.setLoginEncryption(generateEncry(5));
		sysUser.setLoginName(sysUser.getLoginName().trim());
		sysUser.setLoginPwd(Tools.MD5(constData.defaultPassword, sysUser.getLoginEncryption()));
		sysUser.setStatus(0);
		sysUser.setCreateUserId(user.getId());
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}
	
	public ResultTools update(SysUser s, SysUser user) throws Exception {
		
		if(s.getId() == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatusNot(s.getId(),2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		validateInfo(s);
		sysUser.setName(s.getName().trim());
		sysUser.setRoleId(s.getRoleId());
		sysUser.setLoginName(s.getLoginName().trim());
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}
	
	public ResultTools updateInfo(Long id, SysUser user) throws Exception {
		
		if(id == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatusNot(id,2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		return ResultTools.SUCCESS(sysUser);
	}
	
	private void validateInfo(SysUser user) throws BusinessException{
		
		AnnotationUtils.validateEdit(user);
		
		if(user.getIsAdmin() == null || !user.getIsAdmin()){
				
			if(user.getRoleId() == null)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "未选择角色").throwBusinessException();
			
			if(roleRepository.countByIdAndStatus(user.getRoleId(), 0) == 0)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "选择角色不存在").throwBusinessException();
			
		}
		
		if(user.getId() == null){
			long countByName = sysUserRepository.countByLoginNameAndStatusNot(user.getLoginName().trim(),2);
			if(countByName > 0)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "登录名已存在").throwBusinessException();
		}else{
			SysUser sysUser = sysUserRepository.findTop1ByLoginNameAndStatusNot(user.getLoginName().trim(),2);
			if(sysUser != null && !sysUser.getId().equals(user.getId()))
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "登录名已存在").throwBusinessException();
		}
	}

	public ResultTools success(Long id, SysUser user) {

		if(id == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatusNot(id,2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		if(sysUser.getIsAdmin())
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "无法操作管理员账号");
		
		if(sysUser.getStatus() == 0)
			return ResultTools.SUCCESS();
		
		sysUser.setStatus(0);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}
	
	public ResultTools block(Long id, SysUser user) {
		
		if(id == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatusNot(id,2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		if(sysUser.getIsAdmin() != null && sysUser.getIsAdmin())
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "无法操作管理员账号");
		
		if(sysUser.getStatus() == 1)
			return ResultTools.SUCCESS();
		
		sysUser.setStatus(1);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}


	public ResultTools delete(Long id, SysUser user) {
		
		if(id == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatusNot(id,2);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "数据不存在");
		
		if(sysUser.getIsAdmin() != null && sysUser.getIsAdmin())
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "无法操作管理员账号");
		
		sysUser.setStatus(2);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}


	public ResultTools updatePwd(String oldpass, String newpass, SysUser user) throws Exception {
		
		if(Tools.isEmpty(oldpass))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "原密码输入错误");
		
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatus(user.getId(), 0);
		
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "您的账号异常,请联系管理员");
		
		String md5oldpass = Tools.MD5(oldpass, sysUser.getLoginEncryption());
		if(!md5oldpass.equals(sysUser.getLoginPwd()))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "原密码输入错误");
		
		if(oldpass.equals(newpass))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "新密码不能与原密码已知");
		
		if(Tools.isEmpty(newpass))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "新密码不能为空");
		String md5newpass = Tools.MD5(newpass, sysUser.getLoginEncryption());
		sysUser.setLoginPwd(md5newpass);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}

	
}
