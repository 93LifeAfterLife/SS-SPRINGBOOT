package com.ss;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.service.SysUserService;
import com.ss.pj.sys.vo.SysUserDeptVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AopTests {
	@Autowired
	private ApplicationContext ctx;
	
	@Test
	public void testSysUserService() {
		SysUserService userService = ctx.getBean("sysUserServiceImpl", SysUserService.class);
		System.out.println(userService.getClass());
		PageObject<SysUserDeptVo> po = userService.findPageObjects("admin", 1);
		System.out.println("rowCount: " + po.getRowCount());
	}
}
