package com.ss.pj.common.exception;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.pj.common.vo.JsonResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理类
 * 当控制层出现异常时:
 * 1) 首先检测当前controller内是否有异常处理方法
 * 2) 其次检测此类是否有异常处理方法
 * @author td
 */
@ControllerAdvice // 此注解修饰的类为一个全局异常处理类
@Slf4j
public class GlobalExceptionHandler {
	/**
	 * 注解表示异常处理方法
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		log.error(e.getMessage());
		return new JsonResult(e);
	}
	
	/**
	 * Shiro框架异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		JsonResult jr = new JsonResult();
		jr.setState(0);
		if(e instanceof UnknownAccountException) {
			jr.setMessage("账户不存在!");
		}else if(e instanceof LockedAccountException) {
			jr.setMessage("账户已被禁用!");
		}else if(e instanceof IncorrectCredentialsException) {
			jr.setMessage("密码不正确!");
		}else if(e instanceof AuthorizationException) {
			jr.setMessage("没有此操作权限!");
		}else {
			jr.setMessage("系统维护中!");
		}
		e.printStackTrace();
		return jr;
	}
}
