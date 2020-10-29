package com.ss.pj.sys.service;

import org.apache.ibatis.annotations.Param;

import com.ss.pj.common.vo.PageObject;
import com.ss.pj.common.vo.SysUserDeptVo;

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
	int validById(
			@Param("id")Integer id, 
			@Param("valid")Integer valid, 
			@Param("modifiedUser")String modifiedUser);
}
