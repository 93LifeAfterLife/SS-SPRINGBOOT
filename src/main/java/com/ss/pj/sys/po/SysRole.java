package com.ss.pj.sys.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysRole implements Serializable{
	private static final long serialVersionUID = 3086385795981748766L;
	private Integer id;
	private String name;
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String  modifiedUser;
}
