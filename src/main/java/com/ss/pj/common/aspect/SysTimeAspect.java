package com.ss.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@Order(1)
public class SysTimeAspect {
	@Pointcut("bean(sysUserServiceImpl)")
	public void doTime() {}

	@Before("doTime()")
	public void doBefore() {
		log.info("-----doTime()-before");
	}

	@After("doTime()")
	public void doAfter() {
		log.info("-----doTime()-after");
	}

	/**
	 * 核心业务正常结束时执行
	 */
	@AfterReturning("doTime()")
	public void doAfterReturning() {
		log.info("-----doTime()-afterReturning");
	}

	/**
	 * 核心业务异常时执行
	 */
	@AfterThrowing("doTime()")
	public void doAfterThrowing() {
		log.info("-----doTime()-afterThrowing");
	}

	@Around("doTime()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		log.info("-----doTime()-around-before");
		Object result = jp.proceed();
		log.info("-----doTime()-around-after");
		return result;
	}
}
