package com.wj.dao.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wj.dao.utils.DaoParam;
import com.wj.pojo.sys.SysResource;

public interface AuthDao {
	
	long countByRoleIdAndUrl(@Param("roleId")Long roleId,@Param("url")String url);

	List<Map<String, Object>> findByRoleIdAndType(@Param("roleId")Long roleId, @Param("type")Integer type,@Param("parentId")Long parentId);

	long countByUrl(@Param("url")String url,@Param("roleId") Long roleId);

	/**
	 * 获取当前用户资源信息,非管理员 
	 * @param param
	 * @return
	 */
	List<SysResource> findThisResourceByType(DaoParam param);
}
