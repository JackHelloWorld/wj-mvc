package com.wj.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.SysResource;

@Repository
public interface SysResourceRepository extends JpaRepository<SysResource, Long>{

	List<SysResource> findByTypeOrderBySortAsc(Integer type);

	List<SysResource> findByTypeAndParentIdOrderBySortAsc(Integer type, Long parentId);

	long countById(Long id);

	SysResource findTop1ById(Long id);

}
