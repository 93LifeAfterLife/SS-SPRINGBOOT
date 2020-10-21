package com.ss;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ss.pj.sys.dao.SysLogDao;
import com.ss.pj.sys.po.SysLog;

@SpringBootTest
public class MybatisTests {
	private static final Logger Logger = LoggerFactory.getLogger(MybatisTests.class);
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Test
	public void testSysLogDao() {
		int rows = sysLogDao.deleteObjectById(10);
		System.out.println("rows= "+rows);
		Logger.info("rows= "+rows);
	}
	
	@Test
	public void testDeleteObjectsByIds() {
		int rows = sysLogDao.deleteObjectsByIds(11,12,13);
		System.out.println("rows= "+rows);
	}
	
	@Test
	public void testGetRowCount() {
		int rowCount = sysLogDao.getRowCount("admin");
		System.out.println(rowCount);
		int rowCount2 = sysLogDao.getRowCount("");
		System.out.println(rowCount2);
	}
	
	@Test
	public void testFindPageObjects() {
		List<SysLog> list = sysLogDao.findPageObjects("admin", 0, 3);
		for (SysLog sysLog : list) {
			System.out.println(sysLog.getUsername());
		}
	}
}
