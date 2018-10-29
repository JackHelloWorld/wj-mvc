package com.wj.pojo.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_address")
public class Address implements Serializable{

	private static final long serialVersionUID = 3134144347009444235L;
	
	/**地址编码*/
	@Id
	@Column(name="a_code")
	private String code;
	
	/**地址编码简写*/
	@Column(name="a_code_acronym")
	private String codeAcronym;
	
	/**父级code*/
	@Column(name="parent_code")
	private String parentCode;
	
	/**等级*/
	@Column(name="a_level")
	private Integer level;

	/**名称*/
	@Column(name="a_name")
	private String name;
	
	/**城乡分类代码*/
	@Column(name="a_countryside")
	private String countryside;
}
