package com.wj.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.SerialNumber;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumber, Long>{

	public Long countByColumnName(String columnName);
	
}
