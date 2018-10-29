package com.wj.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long>{

	SysUser findTop1ByLoginNameAndStatusNot(String loginName, Integer status);

	long countByRoleIdAndStatusNot(Long roleId, Integer status);

	long countByLoginNameAndStatusNot(String loginName, Integer status);

	SysUser findTop1ByIdAndStatusNot(Long id, Integer status);

	SysUser findTop1ByIdAndStatus(Long id, Integer status);

}
