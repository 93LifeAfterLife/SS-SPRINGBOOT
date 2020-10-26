package com.ss.pj.sys.po;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SysDept implements Serializable{
	private static final long serialVersionUID = -5405773132893903405L;
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer sort;
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
