package com.wj.service.utils;

import com.wj.pojo.sys.SysUser;

public class ThreadData {
	
	public static final ThreadLocal<SysUser> SysUser = new ThreadLocal<>();
	
}
