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
 * 系统登录用户
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_user_login_log")
public class SysUserLoginLog implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**登录用户*/
	@Column(name="user_id")
	private Long userId;
	
	/**登录时间*/
	@Column(name="login_time")
	private Date loginTime;
	
	/**登录Ip*/
	@Column(name="login_ip")
	private String loginIp;

}
