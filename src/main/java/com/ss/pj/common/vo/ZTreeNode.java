package com.ss.pj.common.vo;

import java.io.Serializable;

/**
 * 菜单页树结构节点对象
 * @author td
 */
public class ZTreeNode implements Serializable{
	private static final long serialVersionUID = -243091833854355368L;
	private Integer id;
	private String name;
	private Integer parentId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
