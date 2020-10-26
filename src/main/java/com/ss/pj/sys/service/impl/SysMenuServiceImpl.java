package com.ss.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ss.pj.common.vo.ZTreeNode;
import com.ss.pj.sys.dao.SysMenuDao;
import com.ss.pj.sys.po.SysMenu;
import com.ss.pj.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;

	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		return list;
	}

	@Override
	public List<ZTreeNode> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}

	@Override
	public int insertObject(SysMenu sysMenu) {
		//1. 参数校验
		if (sysMenu == null) {
			throw new IllegalArgumentException("IllegalArgumentException: 所保存对象不能为空!");
		}
		if (StringUtils.isEmpty(sysMenu.getName())) {
			throw new IllegalArgumentException("IllegalArgumentException: 菜单名不能为空!");
		}
		if (StringUtils.isEmpty(sysMenu.getPermission())) {
			throw new IllegalArgumentException("IllegalArgumentException: 权限标识不能为空!");
		}
		//2. 持久化数据
		int rows = sysMenuDao.insertObject(sysMenu);
		//3. 返回结果
		return rows;
	}

	@Override
	public int updateObject(SysMenu sysMenu) {
		if (sysMenu == null) {
			throw new IllegalArgumentException("IllegalArgumentException: 所保存对象不能为空!");
		}
		if (StringUtils.isEmpty(sysMenu.getName())) {
			throw new IllegalArgumentException("IllegalArgumentException: 菜单名不能为空!");
		}
		if (StringUtils.isEmpty(sysMenu.getPermission())) {
			throw new IllegalArgumentException("IllegalArgumentException: 权限标识不能为空!");
		}
		return sysMenuDao.updateObject(sysMenu);
	}
}
