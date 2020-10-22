package com.ss.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 为业务层返回的数据添加状态信息
 * 1) 正常数据
 * 2) 异常数据
 * @author td
 */
@Data
@NoArgsConstructor
public class JsonResult implements Serializable {
	private static final long serialVersionUID = -2773647279809865048L;
	/** 状态码; 1表示SUCCESS, 0表示FAILURE */
	private int state = 1;
	/** 状态信息 */
	private String message = "SUCCESS";
	/** 正确数据 */
	private Object data;
	
	/** 一般查询时调用, 封装查询结果 */
	public JsonResult(Object data) {
		super();
		this.data = data;
	}
	
	/** 自定义返回消息(提示) */
	public JsonResult(String message) {
		super();
		this.message = message;
	}
	
	/** 出现异常时调用 */
	public JsonResult(Throwable t) {
		this.state = 0;
		this.message = t.getMessage();
	}
}
