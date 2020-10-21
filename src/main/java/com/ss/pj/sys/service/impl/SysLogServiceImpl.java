package com.ss.pj.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pj.sys.dao.SysLogDao;
import com.ss.pj.sys.service.SysLogService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	
	/** 使用 ali 推荐的日志 */
	//private static final Logger Logger = LoggerFactory.getLogger(SysLogServiceImpl.class);
	
	@Override
	public int deleteObjectById(Integer id) {
		//1. 参数校验
		//2. 执行删除
		int rows = sysLogDao.deleteObjectById(id);
		//3. 结果校验
		if (rows>0) {
			// Logger.info("delete ok! rows = " + rows);
			// 当使用lombak 插件后会自动编译后植入 Logger
			log.info("delete ok! rows = " + rows);
		}
		return rows;
	}

	@Override
	public int deleteObjectsByIds(Integer... ids) {
		int rows = sysLogDao.deleteObjectsByIds(ids);
		return rows;
	}
}
