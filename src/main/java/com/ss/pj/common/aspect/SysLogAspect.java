package com.ss.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.pj.common.aspect.annotation.RequiredLog;
import com.ss.pj.common.util.IpUtil;
import com.ss.pj.sys.dao.SysLogDao;
import com.ss.pj.sys.po.SysLog;
import com.ss.pj.sys.po.SysUser;

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
// @Order(2)
public class SysLogAspect {
	/**
	 * bean表达式内部指定的bean对象中所有方法为切入点
	 */
	// @Pointcut("bean(sysMenuServiceImpl)")
	@Pointcut("@annotation(com.ss.pj.common.aspect.annotation.RequiredLog)")
	public void logPointCut() {}

	/**
	 * 环绕通知: 目标方法执行前后都可以执行
	 * @param jp
	 * @return 目标方法执行的结果
	 * @throws Throwable
	 */
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		try {
			// System.out.println("around().before");
			long t1 = System.currentTimeMillis();
			log.info("start: "+t1);
			Object result = jp.proceed();	//调用下一个切面方法或目标方法
			long t2 = System.currentTimeMillis();
			log.info("after: "+t2);
			
			// 持久化日志信息
			saveLogObject(jp, t2-t1);
			
			// System.out.println("around().after");
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
	@Autowired
	private SysLogDao sysLogDao;

	/**
	 * 持久化用户行为日志信息
	 * @param jp
	 * @param time
	 * @throws Throwable
	 */
	private void saveLogObject(ProceedingJoinPoint jp, long time) throws Throwable {
		//1. 获取用户行为信息
		//1.1 获取目标对象类型
		Class<?> targetCls = jp.getTarget().getClass();
		//1.2 获取目标类的方法名
		//1.2.1 获取方法签名对象
		MethodSignature ms = (MethodSignature) jp.getSignature();
		//1.2.2 拼接字符串
		String methodName = targetCls.getName() +"."+ ms.getName();
		//2. 封装用户行为信息
		SysLog sysLog = new SysLog();
		sysLog.setIp(IpUtil.getIpAddr());
		//2.1 获取用户信息
		SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
		sysLog.setUsername(sysUser.getUsername());
		sysLog.setMethod(methodName);// 目标类全名+方法
		sysLog.setParams(getRequestParams(jp));// 方法实际参数值
		sysLog.setOperation(getRequestOperation(targetCls, ms));// 基于注解方式获取操作名
		sysLog.setTime(time);
		sysLog.setCreatedTime(new Date());
		//3. 持久化
		sysLogDao.insertObject(sysLog);
	}

	/**
	 * 获取目标类方法上的注解值
	 * @param targetCls
	 * @param ms
	 * @return
	 * @throws Throwable
	 */
	private String getRequestOperation(Class<?> targetCls, MethodSignature ms) throws Throwable {
		//2.1 获取目标类方法上的注解的value属性
		// Method method = ms.getMethod(); // 获取的是接口方法
		Method targetMethod = targetCls.getMethod(ms.getName(), ms.getParameterTypes());
		String value = targetMethod.getDeclaredAnnotation(RequiredLog.class).value();
		//2.2 处理空操作信息
		String operation = "null";
		if (value != null) {
			operation = value;
		}
		return operation;
	}

	/**
	 * 获取请求参数的Json格式字符串
	 * @param jp
	 * @return
	 * @throws JsonProcessingException
	 */
	private String getRequestParams(ProceedingJoinPoint jp) throws JsonProcessingException {
		//2.1 获取参数数组 Ljava.lang.Object;@5874678
		Object[] args = jp.getArgs();
		//2.2 处理空参数
		String params = "[]";
		if (args.length > 0) {
			// 转换为Json串
			params = new ObjectMapper().writeValueAsString(args);
		}
		return params;
	}
}
