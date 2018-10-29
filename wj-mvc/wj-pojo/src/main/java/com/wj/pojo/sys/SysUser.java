package com.wj.pojo.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wj.common.annotation.QueryConfig;
import com.wj.common.annotation.ValidateEdit;
import com.wj.common.annotation.QueryConfig.QueryType;
import com.wj.common.annotation.ValidateEdit.ValidateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统登录用户
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_user")
public class SysUser implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**用户名*/
	@Column(name="u_name")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="请输入用户名")
	private String name;
	
	/**头像*/
	@Column(name="u_profile")
	private String profile;
	
	/**角色*/
	@Column(name="u_role_id")
	private Long roleId;
	
	/**角色*/
	@Transient
	private String roleName;
	
	/**登录名*/
	@Column(name="u_login_name")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(type=ValidateType.NULL,message="请输入登录名")
	private String loginName;
	
	/**是否为管理员账户*/
	@Column(name="u_is_admin")
	private Boolean isAdmin;
	
	/**最后一次登录时间*/
	@Column(name="u_last_login_time")
	private Date lastLoginTime;
	
	/**创建人*/
	@Column(name="u_create_user_id")
	private Long createUserId;
	
	/**创建人*/
	@Transient
	@QueryConfig(QueryType.LIKEBOTH)
	private String createUserName;
	
	/**创建时间*/
	@Column(name="u_create_time")
	private Date createTime;
	
	/**登录密码*/
	@JsonIgnore
	@Column(name="u_login_pwd")
	private String loginPwd;
	
	/**登录密码加密基数*/
	@JsonIgnore
	@Column(name="u_login_encryption")
	private String loginEncryption;
	
	/**状态{0:正常,1:停用,2:已删除}*/
	@Column(name="u_status")
	private Integer status;

	public Boolean getIsAdmin(){
		return this.isAdmin != null && this.isAdmin;
	}
	
}
