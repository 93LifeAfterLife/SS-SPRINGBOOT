package com.ss.pj.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ss.pj.common.vo.ZTreeNode;
import com.ss.pj.sys.po.SysMenu;

@Mapper
// @CacheNamespace(blocking = true)	// 开启缓存, 不推荐使用mybatis的缓存!!!
public interface SysMenuDao {
	/**
	 * 查询所有菜单信息
	 * 1) 一行菜单信息映射成一个map对象(k为记录中的字段名)
	 * 2) 多行记录会对应多个map, 然后将map存在list集合
	 * @return
	 */
	@Select("select c.*, p.name parentName from sys_menus c left join sys_menus p on c.parentId = p.id")
	List<Map<String, Object>> findObjects();
	
	/**
	 * 查询ZTree节点信息
	 * 注意: 一行菜单节点信息映射成一个ZTreeMenuNode对象(映射名称需一致)
	 * @return
	 */
	@Select("select id, name, parentId from sys_menus")
	List<ZTreeNode> findZtreeMenuNodes();
	
	/**
	 * 添加新的菜单信息
	 * @param sysMenu
	 * @return
	 */
	int insertObject(SysMenu sysMenu);
	
	/**
	 * 更新(修改)菜单信息
	 * @param sysMenu
	 * @return
	 */
	int updateObject(SysMenu sysMenu);
	
	/**
	 * 基于菜单id查找权限标识信息
	 * @param menuIds
	 * @return
	 */
	List<String> findPermissionsByMenuIds(
			@Param("menuIds")Integer... menuIds);
	
	/**
	 * 基于菜单id删除菜单
	 * @param menuId
	 * @return
	 */
	@Delete("delete from sys_menus where id = #{id}")
	int deleteObject(Integer id);
	
	/**
	 * 获取节点下的子元素(节点)个数
	 * @param id
	 * @return
	 */
	@Select("select count(*) from sys_menus where parentId=#{id}")
	int getChildCount(Integer id);
}
