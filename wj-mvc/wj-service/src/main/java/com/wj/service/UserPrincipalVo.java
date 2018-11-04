package com.wj.service;

import java.io.Serializable;
import java.security.Principal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipalVo implements Principal,Serializable{
	
	private static final long serialVersionUID = -7546215622447539833L;
	
	private String name;

}
