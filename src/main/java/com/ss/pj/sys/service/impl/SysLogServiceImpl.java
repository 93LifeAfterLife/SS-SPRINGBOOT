package com.ss.pj.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pj.common.aspect.annotation.RequiredLog;
import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.util.PageUtil;
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
	@RequiresPermissions("sys:log:delete")
	@RequiredLog("delete logs")
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
	@RequiresPermissions("sys:log:delete")
	@RequiredLog("delete logs")
	public int deleteObjectsByIds(Integer... ids) {
		//1. 参数校验
		if (ids == null||ids.length == 0) {
			throw new IllegalArgumentException("IllegalArgumentException: 请至少选择一项进行操作!");
		}
		//2. 执行删除操作
		int rows;
		try {
			rows = sysLogDao.deleteObjectsByIds(ids);
		} catch (Throwable e) {
			e.printStackTrace();
			// 发出警报信息, 例如给运维人员发消息
			throw new ServiceException("ServiceException: 系统故障, 正在恢复中...");
		}
		//3. 对结果进行验证
		if (rows == 0) {
			throw new ServiceException("ServiceException: 记录可能已经不存在, 请重新操作!");
		}
		//4. 返回结果
		return rows;
	}

	@Override
	@RequiresPermissions("sys:log:view")
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		//1. 参数校验
		if (pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("IllegalArgumentException: 页码不正确!");
		//2. 查询总记录数并进行校验
		int rowCount = sysLogDao.getRowCount(username);
		if (rowCount==0)
			throw new ServiceException("ServiceException: 记录不存在!");
		//3. 查询当前页要呈现的记录
		int pageSize = PageUtil.getPageSize();
		int startIndex = PageUtil.getStartIndex(pageCurrent);
		List<SysLog> records = sysLogDao.findPageObjects(username, startIndex, pageSize);
		//4. 对查询结果进行计算和封装
		PageObject<SysLog> po = PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
		//5. 返回结果
		return po;
	}
}
