package com.wj.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.SysUserLoginLog;

@Repository
public interface SysUserLoginLogRepository extends JpaRepository<SysUserLoginLog, Long>{
	
}
