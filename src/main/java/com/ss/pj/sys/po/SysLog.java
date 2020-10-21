package com.ss.pj.sys.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * 封装用户的行为日志信息
 * @author td
 */
@Data
@ToString
public class SysLog implements Serializable {
	private static final long serialVersionUID = 3401654542716012414L;
	/** 用户id */
	private Integer id;
	/** 用户名 */
	private String username;
	/** 用户操作 */
	private String operation;
	/** 请求方法 */
	private String method;
	/** 请求参数 */
	private String params;
	/** 执行时长(毫秒) */
	private Long time;
	/** ip地址 */
	private String ip;
	/** 创建时间 */
	private Date createdTime;
}
