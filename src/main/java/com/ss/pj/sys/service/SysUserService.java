package com.ss.pj.sys.service;

import java.util.Map;

import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.po.SysUser;
import com.ss.pj.sys.vo.SysUserDeptVo;

public interface SysUserService {
	/**
	 * 查询当前页信息
	 * @param username
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent);

	/**
	 * 用户禁用启用操作
	 * @param id
	 * @param valid
	 * @param modifiedUser
	 * @return
	 */
	int validById(Integer id, Integer valid, String modifiedUser);
	
	/**
	 * 保存新增的用户信息
	 * @param sysUser
	 * @param roleIds
	 * @return
	 */
	int saveObject(SysUser sysUser, Integer... roleIds);
	
	/**
	 * 根据用户id查询用户信息,部门信息以及对应的角色信息
	 * @param id
	 * @return
	 */
	Map<String, Object> findObjectById(Integer userId);
	
	/**
	 * 更新用户信息到相关联的表中
	 * @param sysUser
	 * @param roleIds
	 * @return
	 */
	int updateObject(SysUser sysUser, Integer... roleIds);
}
