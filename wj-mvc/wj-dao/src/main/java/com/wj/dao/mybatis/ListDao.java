package com.wj.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.wj.pojo.sys.Role;
import com.wj.pojo.sys.SysUser;

public interface ListDao {
	
	List<Map<String, Object>> roleList(Role role);

	List<Map<String, Object>> sysUserList(SysUser user);
	
}
