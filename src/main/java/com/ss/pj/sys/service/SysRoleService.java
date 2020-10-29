package com.ss.pj.sys.service;

import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.po.SysRole;
import com.ss.pj.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	/**
	 * 分页查询角色信息,并查询角色总记录数据
	 * @param name
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysRole> findPageObjects(String name, Integer pageCurrent);
	
	/**
	 * 基于id删除角色信息(用户-角色-菜单)
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	/**
	 * 将角色信息写入到数据库
	 * @param sysRole
	 * @return
	 */
	int insertObject(SysRole sysRole, Integer... menuIds);
	
	/**
	 * 通过id查询获取角色和菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 修改角色信息
	 * @param sysRole
	 * @return
	 */
	int updateObject(SysRole sysRole, Integer... menuIds);
}
