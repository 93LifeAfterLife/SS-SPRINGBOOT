package com.ss.pj.sys.service;

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
}
