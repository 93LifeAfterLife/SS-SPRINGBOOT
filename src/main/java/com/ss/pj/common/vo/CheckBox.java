package com.ss.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 借助此对象封装角色id,角色名称
 * @author td
 */
@Data
@ToString
public class CheckBox implements Serializable{
	private static final long serialVersionUID = -4782016837009614924L;
	private Integer id;
	private String name;
}
