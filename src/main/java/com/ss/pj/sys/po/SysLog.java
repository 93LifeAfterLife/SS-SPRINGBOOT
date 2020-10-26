package com.ss.pj.sys.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装用户的行为日志信息
 * @author td
 */
public class SysLog implements Serializable {
	private static final long serialVersionUID = -3510918771659331664L;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
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
