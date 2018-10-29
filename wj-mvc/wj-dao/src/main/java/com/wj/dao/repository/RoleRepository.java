package com.wj.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findTop1ByIdAndStatusNot(Long id, Integer status);

	Role findTop1ByNameAndStatusNot(String name, Integer status);

	List<Role> findByStatus(Integer status);

	long countByIdAndStatus(Long roleId, Integer status);

	long countByNameAndStatusNot(String name, Integer status);

	long countByNameAndStatusNotAndIdNot(String name, Integer status, Long id);
	
}
