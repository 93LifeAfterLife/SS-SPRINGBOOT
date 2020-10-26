package com.ss.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ss.pj.common.vo.SysUserDeptVo;

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
}
