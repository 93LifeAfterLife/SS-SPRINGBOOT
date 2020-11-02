package com.ss.pj.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ss.pj.common.aspect.annotation.RequiredLog;
import com.ss.pj.common.exception.ServiceException;
import com.ss.pj.common.util.PageUtil;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.dao.SysUserDao;
import com.ss.pj.sys.dao.SysUserRoleDao;
import com.ss.pj.sys.po.SysUser;
import com.ss.pj.sys.service.SysUserService;
import com.ss.pj.sys.vo.SysUserDeptVo;

@Service
@Transactional(
		timeout = 30, 
		readOnly = false, 
		isolation = Isolation.READ_COMMITTED,
		rollbackFor = Throwable.class,
		propagation = Propagation.REQUIRED)
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@RequiredLog("query user")
	@Transactional(readOnly = true)
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

	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1. 验证
		if (id==null || id<=0) {
			throw new IllegalArgumentException("IllegalArgumentException: 参数不合法!id="+id);
		}
		if (valid!=1 && valid!=0) {
			throw new IllegalArgumentException("IllegalArgumentException: 参数不合法!valid="+valid);
		}
		if (StringUtils.isEmpty(modifiedUser)) {
			throw new ServiceException("ServiceException: 修改用户不能为空!");
		}
		//2. 执行操作
		int rows = 0;
		try {
			rows = sysUserDao.validById(id, valid, modifiedUser);
		} catch (Throwable e) {
			e.printStackTrace();
			// 给维护人员消息
			throw new ServiceException("ServiceException: 后台正在维护中!");
		}
		//3. 判定结果
		if (rows==0) {
			throw new ServiceException("ServiceException: 此记录可能已经不存在!");
		}
		return rows;
	}

	@Transactional(readOnly = true)
	@Override
	public int saveObject(SysUser sysUser, Integer... roleIds) {
		//1. 验证
		if (sysUser == null) {
			throw new NullPointerException("NullPointerException: 保存对象不能为空!");
		}
		if (StringUtils.isEmpty(sysUser.getUsername())) {
			throw new IllegalArgumentException("IllegalArgumentException: 用户名不能为空!");
		}
		if (StringUtils.isEmpty(sysUser.getPassword())) {
			throw new IllegalArgumentException("IllegalArgumentException: 密码不能为空!");
		}
		if (roleIds==null||roleIds.length==0) {
			throw new ServiceException("ServiceException: 至少为用户分配一个角色!");
		}
		//2. 写入数据库
		String salt = UUID.randomUUID().toString();
		sysUser.setSalt(salt);
		// 加密
		SimpleHash simpleHash = new SimpleHash("MD5", sysUser.getPassword(), salt);
		sysUser.setPassword(simpleHash.toHex());
		int rows = sysUserDao.insertObjects(sysUser);
		sysUserRoleDao.insertObject(sysUser.getId(), roleIds);
		// 返回结果
		return rows;
	}

	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		//1. 验证
		if (userId == null || userId<=0) {
			throw new IllegalArgumentException("IllegalArgumentException: 参数不合法!userId="+userId);
		}
		//2. 执行查询
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		if (user == null) {
			throw new NullPointerException("NullPointerException: 此用户已经不存在!");
		}
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
		//3. 封装数据
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		//4. 返回数据
		return map;
	}

	@Override
	public int updateObject(SysUser sysUser, Integer... roleIds) {
		//1. 验证
		if (sysUser==null) {
			throw new NullPointerException("NullPointerException: 保存对象不能为空!");
		}
		if (StringUtils.isEmpty(sysUser.getUsername())) {
			throw new IllegalArgumentException("IllegalArgumentException: 用户名不能为空!");
		}
		if (roleIds==null|roleIds.length==0) {
			throw new IllegalArgumentException("IllegalArgumentException: 至少为用户指定一个角色!");
		}
		// 还有等等其他验证, 比如用户名已然存在, 密码不合格等等!
		//2. 执行操作
		int rows = sysUserDao.updateObject(sysUser);
		sysUserRoleDao.deleteObjectsByUserId(sysUser.getId());
		sysUserRoleDao.insertObject(sysUser.getId(), roleIds);
		//3. 返回
		return rows;
	}
}
