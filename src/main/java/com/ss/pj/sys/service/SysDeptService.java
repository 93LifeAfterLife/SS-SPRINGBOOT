package com.ss.pj.sys.service;
import java.util.List;
import java.util.Map;

import com.ss.pj.sys.po.SysDept;
import com.ss.pj.common.vo.ZTreeNode;

public interface SysDeptService {
	int saveObject(SysDept entity);
	int updateObject(SysDept entity);
	
	List<ZTreeNode> findZTreeNodes();
	List<Map<String,Object>> findObjects();
	
	int deleteObject(Integer id);
}
