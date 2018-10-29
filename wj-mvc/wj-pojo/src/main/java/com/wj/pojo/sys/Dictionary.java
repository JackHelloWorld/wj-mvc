package com.wj.pojo.sys;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wj.common.annotation.ValidateEdit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典信息
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_dictionary")
public class Dictionary implements Serializable{

	private static final long serialVersionUID = 7794669285253606814L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	/**显示名称*/
	@Column(name="d_text")
	@ValidateEdit(message="请输入字典名称")
	private String text;
	
	/**英文名称*/
	@Column(name="d_en_text")
	@ValidateEdit(message="请输入字典英文名称")
	private String enText;
	
	/**值*/
	@Column(name="d_value")
	@ValidateEdit(message="请输入字典值")
	private String value;
	
	/**标识码*/
	@ValidateEdit(message="请输入token")
	@Column(name="d_token")
	private String token;
	
	/**排序*/
	@Column(name="d_sort")
	private Integer sort;
	
	/**状态:{0:正常,1:已删除}*/
	@Column(name="d_status")
	private Integer status;
	
	/**父级id,0为顶级*/
	@Column(name="d_parent_token")
	private String parentToken;
	
	/**子节点信息*/
	@Transient
	private List<Dictionary> nodes;
}
