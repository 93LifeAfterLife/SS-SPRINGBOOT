package com.ss.pj.sys.service;

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
}
