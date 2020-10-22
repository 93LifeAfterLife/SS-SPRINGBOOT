package com.ss.pj.common.vo;

import java.io.Serializable;

/**
 * 为业务层返回的数据添加状态信息
 * 1) 正常数据
 * 2) 异常数据
 * @author td
 */
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -6383283829159086665L;
	/** 状态码; 1表示SUCCESS, 0表示FAILURE */
	private int state = 1;
	/** 状态信息 */
	private String message = "SUCCESS";
	/** 正确数据 */
	private Object data;
	
	public JsonResult() {
	}

	/** 一般查询时调用, 封装查询结果 */
	public JsonResult(Object data) {
		this.setData(data);
	}
	
	/** 自定义返回消息(提示) */
	public JsonResult(String message) {
		this.setMessage(message);
	}
	
	/** 出现异常时调用 */
	public JsonResult(Throwable t) {
		this.setState(0);
		this.setMessage(t.getMessage());
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
