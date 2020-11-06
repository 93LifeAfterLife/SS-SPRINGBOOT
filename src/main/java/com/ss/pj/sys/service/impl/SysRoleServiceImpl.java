package com.ss.pj.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ss.pj.common.aspect.annotation.RequiredLog;
import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.util.PageUtil;
import com.ss.pj.common.vo.CheckBox;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.dao.SysRoleDao;
import com.ss.pj.sys.dao.SysRoleMenuDao;
import com.ss.pj.sys.dao.SysUserRoleDao;
import com.ss.pj.sys.po.SysRole;
import com.ss.pj.sys.service.SysRoleService;
import com.ss.pj.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Override
	@RequiresPermissions("sys:role:view")
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1. 验证参数合法性
		//1.1 验证pageCurrent合法性
		if (pageCurrent==null || pageCurrent<1) {
			throw new IllegalArgumentException("IllegalArgumentException: 当前页码不正确!");
		}
		//2. 查询总记录数并进行校验
		int rowCount = sysRoleDao.getRowCount(name);
		if (rowCount==0)
			throw new ServiceException("ServiceException: 记录不存在!");
		//3. 查询当前页要呈现的记录
		int pageSize = PageUtil.getPageSize();
		int startIndex = PageUtil.getStartIndex(pageCurrent);
		List<SysRole> records = sysRoleDao.doFindPageObjects(name, startIndex, pageSize);
		//4. 对查询结果进行计算和封装
		PageObject<SysRole> po = PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
		//5. 返回结果
		return po;
	}

	@Override
	@RequiresPermissions("sys:role:delete")
	@RequiredLog("delete role")
	public int deleteObject(Integer id) {
		//1. 验证参数合法性
		if (id==null || id<1) {
			throw new IllegalArgumentException("IllegalArgumentException: id的值不正确, id="+id);
		}
		//2. 执行删除操作
		int rows = sysRoleDao.deleteObject(id);
		if (rows == 0) {
			throw new ServiceException("ServiceException: 数据可能已经不存在!");
		}
		//2.1 删除中间关系表信息
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectsByRoleId(id);
		return 0;
	}

	@Override
	@RequiresPermissions("sys:role:add")
	@RequiredLog("add role")
	public int insertObject(SysRole sysRole, Integer...menuIds) {
		//1. 合法性验证
		if (sysRole == null) {
			throw new NullPointerException("NullPointerException: 保存数据不能为空!");
		}
		if (StringUtils.isEmpty(sysRole.getName())) {
			throw new IllegalArgumentException("IllegalArgumentException: 角色名不能为空!");
		}
		if (menuIds == null || menuIds.length == 0) {
			throw new ServiceException("ServiceException: 必须为角色赋予权限!");
		}
		//2. 执行保存操作
		int rows = sysRoleDao.insertObject(sysRole);
		sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		return rows;
	}

	@Override
	@RequiresPermissions("sys:role:add")
	public SysRoleMenuVo findObjectById(Integer id) {
		//1. 验证
		if (id ==null || id <=0) {
			throw new IllegalArgumentException("IllegalArgumentException: id的值不合法!");
		}
		//2. 执行查询
		SysRoleMenuVo result = sysRoleDao.findObjectById(id);
		//3. 验证结果
		if (result==null) {
			throw new ServiceException("ServiceException: 此记录已经不存在!");
		}
		return result;
	}

	@Override
	@RequiresPermissions("sys:role:update")
	@RequiredLog("update role")
	public int updateObject(SysRole sysRole, Integer... menuIds) {
		//1. 验证
		if (sysRole == null) {
			throw new NullPointerException("NullPointerException: 更新的对象不能为空!");
		}
		if (sysRole.getId() == null) {
			throw new IllegalArgumentException("IllegalArgumentException: id的值不能为空!");
		}
		if (StringUtils.isEmpty(sysRole.getName())) {
			throw new IllegalArgumentException("IllegalArgumentException: 角色名不能为空!");
		}
		if (menuIds == null || menuIds.length==0) {
			throw new ServiceException("ServiceException: 必须为角色赋予权限!");
		}
		//2. 执行更新
		int rows = sysRoleDao.updateObject(sysRole);
		if (rows == 0) {
			throw new ServiceException("ServiceException: 执行修改的对象可能已经不存在!");
		}
		sysRoleMenuDao.deleteObjectsByRoleId(sysRole.getId());
		sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		//3. 返回结果
		return rows;
	}

	@Override
	@RequiresPermissions("sys:role:view")
	public List<CheckBox> findObjects() {
		return sysRoleDao.findObjects();
	}
}
