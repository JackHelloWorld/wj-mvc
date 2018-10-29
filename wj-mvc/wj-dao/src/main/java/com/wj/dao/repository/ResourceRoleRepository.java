package com.wj.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.ResourceRole;

@Repository
public interface ResourceRoleRepository extends JpaRepository<ResourceRole, Long>{

	@Modifying
	void deleteByRoleId(Long roleId);
	
}
