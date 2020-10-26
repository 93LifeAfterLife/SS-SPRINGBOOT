package com.ss.pj.common.vo;

import java.io.Serializable;
import java.util.Date;

import com.ss.pj.sys.po.SysDept;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysUserDeptVo implements Serializable{
	private static final long serialVersionUID = 8209331179778555833L;
	private Integer id;
	private String username;
	private String password;//md5
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private SysDept sysDept; //private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
