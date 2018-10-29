package com.wj.pojo.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色资源权限
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_resource_role")
public class ResourceRole implements Serializable{

	private static final long serialVersionUID = -4303765635628600857L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**角色id*/
	@Column(name="role_id")
	private Long roleId;
	
	/**资源id*/
	@Column(name="resource_id")
	private Long resourceId;
	
	/**创建时间*/
	@Column(name="create_time")
	private Date createTime;
	
	/**授权操作人*/
	@Column(name="create_user")
	private Long createUser;

}
