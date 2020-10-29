package com.ss.pj.sys.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysUser implements Serializable{
	private static final long serialVersionUID = -9129663615829985864L;
	private Integer id;
	private String username;
	private String password;
	/** 加密盐值 */
	private String salt;	
	private String email;
	private String mobile;
	private Integer valid=1;
	private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
