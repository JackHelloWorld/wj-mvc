package com.wj.pojo.sys;

import java.io.Serializable;

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
 * 序列号记录
 * @author jack
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_serial_number")
public class SerialNumber implements Serializable{

	private static final long serialVersionUID = 2988989312275381148L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	public SerialNumber(String columnName) {
		this.columnName = columnName;
	}

	/**列名*/
	@Column(name="f_column_name")
	private String columnName;

}
