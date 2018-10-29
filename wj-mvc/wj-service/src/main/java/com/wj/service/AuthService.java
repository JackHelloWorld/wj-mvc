package com.wj.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wj.common.config.Const;
import com.wj.dao.mybatis.AuthDao;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.BaseService;

@Service
public class AuthService extends BaseService{

	@Resource
	Const constData;
	
	@Resource
	AuthDao authDao;
	
	public boolean validate(SysUser user, String url) {
		if(user.getIsAdmin()) 
			return true;
		return authDao.countByUrl(url,user.getRoleId()) > 0;
	}

}
