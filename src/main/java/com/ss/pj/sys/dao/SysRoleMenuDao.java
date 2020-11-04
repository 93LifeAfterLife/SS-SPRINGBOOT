package com.ss.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {
	/**
	 * 基于角色id删除角色菜单
	 * @param roleId
	 * @return
	 */
	@Delete("delete from sys_role_menus where role_id=#{roleId}")
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 插入角色菜单关系数据
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	int insertObjects(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer... menuIds);
	
	/**
	 * 基于角色id查询菜单ids
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(
			@Param("roleIds")Integer... roleIds);
}
