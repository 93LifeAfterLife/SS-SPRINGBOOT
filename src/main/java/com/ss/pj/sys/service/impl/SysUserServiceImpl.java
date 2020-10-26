package com.ss.pj.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.util.PageUtil;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.common.vo.SysUserDeptVo;
import com.ss.pj.sys.dao.SysUserDao;
import com.ss.pj.sys.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;

	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		//1. 参数校验
		if (pageCurrent==null || pageCurrent<1)
			throw new IllegalArgumentException("IllegalArgumentException: 页码不正确!");
		//2. 查询总记录数并进行校验
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount==0)
			throw new ServiceException("ServiceException: 记录不存在!");
		//3. 查询当前页要呈现的记录
		int pageSize = PageUtil.getPageSize();
		int startIndex = PageUtil.getStartIndex(pageCurrent);
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		//4. 对查询结果进行计算和封装
		PageObject<SysUserDeptVo> po = PageUtil.newPageObject(pageCurrent, rowCount, pageSize, records);
		//5. 返回结果
		return po;
	}
}
