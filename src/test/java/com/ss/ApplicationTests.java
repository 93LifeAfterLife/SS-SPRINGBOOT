package com.ss;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.ss.pj.common.cache.DefaultCache;

@RunWith(SpringRunner.class)	//指定单元测试启动类 (junit + spring-test)
@SpringBootTest	//执行spring boot 初始化
class ApplicationTests {
	@Autowired
	private ApplicationContext ctx;
	@Test
	void contextLoads() {
		System.out.println(ctx);
	}
	
	@Test
	public void testCache() {
		DefaultCache cache = ctx.getBean(DefaultCache.class);
		/** 断言测试 */
		Assert.assertNotEquals(null, cache);
	}
}
