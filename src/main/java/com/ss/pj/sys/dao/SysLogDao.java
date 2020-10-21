package com.ss.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ss.pj.sys.po.SysLog;

@Mapper
public interface SysLogDao {
	@Delete("delete from sys_logs where id = #{id}")
	int deleteObjectById(Integer id);
	
	/** 复杂SQL建议写在xml文件中 */
	int deleteObjectsByIds(@Param("ids")Integer... ids);
	
	/**
	 * 按条件查询日志总记录数
	 * @param username 查询条件
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	
	/**
	 * 基于条件执行分页查询, 获取当前页记录
	 * @param username 查询条件
	 * @param startIndex 起始位置	
	 * @param pageSize 页面大小(显示多少条数据)
	 * @return
	 */
	List<SysLog> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
}
