package com.ss.pj.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用aspect注解描述的类为一个切面类对象
 * 1) pointcut-切入点: 切入扩展功能的点
 * 2) advice-通知: 扩展功能
 * @author td
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class SysLogAspect {
	/**
	 * bean表达式内部指定的bean对象中所有方法为切入点
	 */
	@Pointcut("bean(*ServiceImpl)")
	public void logPointCut() {}

	/**
	 * 环绕通知: 目标方法执行前后都可以执行
	 * @param jp
	 * @return 目标方法执行的结果
	 * @throws Throwable
	 */
	@Around(value = "logPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		try {
			// System.out.println("around().before");
			log.info("start: "+System.currentTimeMillis());
			Object result = jp.proceed();	//调用下一个切面方法或目标方法
			log.info("after: "+System.currentTimeMillis());
			// System.out.println("around().after");
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
}
