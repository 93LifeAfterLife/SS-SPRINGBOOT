package com.ss.pj.sys.service;

import java.util.List;
import java.util.Map;

import com.ss.pj.common.vo.ZTreeNode;
import com.ss.pj.sys.po.SysMenu;

public interface SysMenuService {
	/**
	 * 查询所有菜单信息并呈现在TreeGrid中
	 * @return
	 */
	List<Map<String, Object>> findObjects();
	
	/**
	 * 查询所有菜单节点信息并呈现在ZTree中
	 * @return
	 */
	List<ZTreeNode> findZtreeMenuNodes();
	
	/**
	 * 保存新的菜单信息
	 * @param sysMenu
	 * @return
	 */
	int insertObject(SysMenu sysMenu);
	
	/**
	 * 更新菜单信息
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
}
