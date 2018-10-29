package com.wj.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String>{

	List<Address> findByParentCode(String parentCode);

	Address findTop1ByCodeAcronym(String codeAcronym);
	
}
