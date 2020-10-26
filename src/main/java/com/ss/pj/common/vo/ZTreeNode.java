package com.ss.pj.common.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

/**
 * 菜单页树结构节点对象
 * @author td
 */
@Data
@ToString
public class ZTreeNode implements Serializable{
	private static final long serialVersionUID = 1072633642811326609L;
	private Integer id;
	private String name;
	private Integer parentId;
}
