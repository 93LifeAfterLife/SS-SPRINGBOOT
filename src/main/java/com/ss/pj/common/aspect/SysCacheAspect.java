package com.ss.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class SysCacheAspect {
	//@Pointcut("execution(* com.ss.pj.sys.service..*.find*(..))")
	@Pointcut("@annotation(com.ss.pj.common.annotation.RequiredCache)")
	public void doCachePointCut() {}
	
	@Around("doCachePointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		log.info("从cache取数据...");
		Object result = jp.proceed();
		log.info("将结果存入cache...");
		return result;
	}
}
