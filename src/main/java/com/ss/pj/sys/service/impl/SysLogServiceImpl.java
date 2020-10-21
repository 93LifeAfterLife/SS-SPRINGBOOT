package com.ss.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.dao.SysLogDao;
import com.ss.pj.sys.po.SysLog;
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

	@Override
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		//1. 参数校验
		if (pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("页码不正确!");
		//2. 查询总记录数并进行校验
		int rowCount = sysLogDao.getRowCount(username);
		if (rowCount==0)
			throw new ServiceException("记录不存在!");
		//3. 查询当前页要呈现的记录
		int pageSize = 3;
		int startIndex = pageSize*(pageCurrent-1);
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
		//4. 对查询结果进行计算和封装
		PageObject<SysLog> po = new PageObject<>();
		po.setRowCount(rowCount);
		po.setRecords(records);
		po.setPageSize(pageSize);
		po.setPageCount((rowCount-1)/pageSize + 1);
		po.setPageCurrent(pageCurrent);
		//5. 返回结果
		return po;
	}
}
