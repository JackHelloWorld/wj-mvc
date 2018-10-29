package com.wj.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdateDto implements Serializable{


	private static final long serialVersionUID = 3134144347009444235L;
	
	/**公司编码*/
	private String no;

	/**公司名称*/
	private String name;
	
	/**公司简介*/
	private String intro;

}
