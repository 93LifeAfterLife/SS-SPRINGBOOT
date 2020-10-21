package com.ss;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ss.pj.sys.dao.SysLogDao;

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
}
