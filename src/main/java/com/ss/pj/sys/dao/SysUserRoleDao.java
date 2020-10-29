package com.ss.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserRoleDao {
	/**
	 * 基于角色id删除用户角色
	 * @param roleId
	 * @return
	 */
	@Delete("delete from sys_user_roles where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 负责将用户与角色的关系数据写入到数据库
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObject(
			@Param("userId")Integer userId,
			@Param("roleIds")Integer... roleIds);
}
