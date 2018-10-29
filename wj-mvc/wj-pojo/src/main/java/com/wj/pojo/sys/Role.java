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

import com.wj.common.annotation.QueryConfig;
import com.wj.common.annotation.ValidateEdit;
import com.wj.common.annotation.QueryConfig.QueryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_role")
public class Role implements Serializable{

	private static final long serialVersionUID = 3134144347009444235L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**角色名称*/
	@Column(name="r_name")
	@QueryConfig(QueryType.LIKEBOTH)
	@ValidateEdit(message="请输入角色名称")
	private String name;
	
	/**创建时间*/
	@Column(name="create_time")
	private Date createTime;
	
	/**创建人*/
	@Column(name="create_user")
	private Long createUser;
	
	/**角色简介*/
	@Column(name="r_intro")
	@ValidateEdit(message="请输入角色简介")
	@QueryConfig(QueryType.LIKEBOTH)
	private String intro;
	
	@Transient
	private String createUserName;
	
	/**状态{0:正常,3:已删除}*/
	@Column(name="r_status")
	private Integer status;
	
	/**删除操作人*/
	@Column(name="r_delete_user_id")
	private Long deleteUserId;
	
	/**删除操作时间*/
	@Column(name="r_delete_time")
	private Date deleteTime;

}
