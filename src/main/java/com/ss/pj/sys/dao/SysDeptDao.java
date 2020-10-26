package com.ss.pj.sys.dao;
import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ss.pj.common.vo.ZTreeNode;
import com.ss.pj.sys.po.SysDept;

@Mapper
public interface SysDeptDao {
	int updateObject(SysDept entity);
	int insertObject(SysDept entity);
	List<ZTreeNode> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	int getChildCount(Integer id);
	int deleteObject(Integer id);
}
