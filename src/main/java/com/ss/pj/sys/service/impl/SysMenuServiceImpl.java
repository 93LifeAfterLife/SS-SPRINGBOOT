package com.ss.pj.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ss.pj.common.aspect.annotation.RequiredLog;
import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.vo.ZTreeNode;
import com.ss.pj.sys.dao.SysMenuDao;
import com.ss.pj.sys.po.SysMenu;
import com.ss.pj.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService {
	@Autowired
	private SysMenuDao sysMenuDao;

	// @RequiredCache
	@Override
	@RequiresPermissions("sys:menu:view")
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		return list;
	}

	@Override
	public List<ZTreeNode> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}

	@Override
	@RequiresPermissions("sys:menu:add")
	@RequiredLog("add menu")
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
	@RequiresPermissions("sys:menu:update")
	@RequiredLog("update menu")
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

	@Override
	@RequiresPermissions("sys:menu:delete")
	@RequiredLog("delete menu")
	public int deleteObject(Integer id) {
		//1.合法性验证
		if(id==null||id<=0)
			throw new ServiceException("数据不合法,id="+id);
		//2.执行删除操作
		//2.1判定此id对应的菜单是否有子元素
		int childCount=sysMenuDao.getChildCount(id);
		if(childCount>0)
			throw new ServiceException("此元素有子元素，不允许删除");
		//2.2判定此部门是否有用户
		//int userCount=sysUserDao.getUserCountByDeptId(id);
		//if(userCount>0)
		//throw new ServiceException("此部门有员工，不允许对部门进行删除");
		//2.2判定此部门是否已经被用户使用,假如有则拒绝删除
		//2.3执行删除操作
		int rows=sysMenuDao.deleteObject(id);
		if(rows==0)
			throw new ServiceException("此信息可能已经不存在");
		return rows;
	}
}
