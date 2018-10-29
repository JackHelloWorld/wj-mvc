package com.wj.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wj.pojo.sys.Dictionary;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long>{

	@Query("from Dictionary where status = 0  order by sort asc")
	List<Dictionary> findAllOrderBySortAsc();

	long countByTokenAndStatus(String token,Integer status);

	Dictionary findTop1ByTokenAndStatus(String token,Integer status);

	long countByIdAndStatus(Long id, Integer status);

	Dictionary findTop1ByIdAndStatus(Long id,Integer status);
	
}
