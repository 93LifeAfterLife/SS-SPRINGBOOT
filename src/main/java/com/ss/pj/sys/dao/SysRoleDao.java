package com.ss.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ss.pj.common.vo.CheckBox;
import com.ss.pj.sys.po.SysRole;
import com.ss.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {
	/**
	 * 基于条件查询当前页数据
	 * @param name
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<SysRole> doFindPageObjects(
			@Param("name")String name, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);
	
	/**
	 * 基于条件查询总记录数
	 * @param name
	 * @return
	 */
	int getRowCount(@Param("name")String name);
	
	/**
	 * 基于id删除角色信息(用户-角色-菜单)
	 * @param id
	 * @return
	 */
	@Delete("delete from sys_roles where id = #{id}")
	int deleteObject(Integer id);
	
	/**
	 * 将角色信息写入到数据库
	 * @param sysRole
	 * @return
	 */
	int insertObject(SysRole sysRole);

	/**
	 * 通过id查询获取角色和菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 修改角色信息
	 * @param sysRole
	 * @return
	 */
	int updateObject(SysRole sysRole);
	
	/**
	 * 查询角色ID,角色名
	 * @return
	 */
	List<CheckBox> findObjects();
}
