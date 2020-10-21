package com.ss.pj.sys.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysLogDao {
	@Delete("delete from sys_logs where id = #{id}")
	int deleteObjectById(Integer id);
	
	/** 复杂SQL建议写在xml文件中 */
	int deleteObjectsByIds(@Param("ids")Integer... ids);
}
