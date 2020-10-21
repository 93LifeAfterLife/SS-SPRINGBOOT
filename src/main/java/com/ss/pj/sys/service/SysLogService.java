package com.ss.pj.sys.service;

import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.po.SysLog;

public interface SysLogService {
	/**
	 * 基于id删除日志记录
	 * @param id
	 * @return
	 */
	int deleteObjectById(Integer id);
	
	/**
	 * 基于多个id删除日志记录
	 * @param ids
	 * @return
	 */
	int deleteObjectsByIds(Integer... ids);
	
	PageObject<SysLog> findPageObjects(String username, Integer pageCurrent);
}
