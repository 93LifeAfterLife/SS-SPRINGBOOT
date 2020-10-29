package com.ss.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ss.pj.sys.po.SysUser;
import com.ss.pj.sys.vo.SysUserDeptVo;

@Mapper
public interface SysUserDao {
	/**
	 * 查询当前页信息
	 * @param username
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysUserDeptVo> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	
	/**
	 * 根据条件查询总记录数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	
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
	
	/**
	 * 负责将用户信息写入到数据库
	 * @param sysUser
	 * @return
	 */
	int insertObjects(SysUser sysUser);
	
	/**
	 * 根据id查询用户以及部门
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	
	/**
	 * 更新用户数据
	 * @param sysUser
	 * @return
	 */
	int updateObject(SysUser sysUser);
}
