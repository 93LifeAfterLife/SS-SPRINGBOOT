package com.ss.pj.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.pj.common.vo.JsonResult;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.po.SysUser;
import com.ss.pj.sys.service.SysUserService;
import com.ss.pj.sys.vo.SysUserDeptVo;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}

	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username, Integer pageCurrent) {
		PageObject<SysUserDeptVo> po = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(po);
	}

	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id, Integer valid) {
		// "admin" 将来是动态获取登陆用户
		sysUserService.validById(id, valid, "admin");
		return new JsonResult("update ok!");
	}

	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}

	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser sysUser, Integer... roleIds) {
		sysUserService.saveObject(sysUser, roleIds);
		return new JsonResult("save ok!");
	}

	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysUserService.findObjectById(id));
	}

	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser entity, Integer... roleIds){
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok!");
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password) {
		//1. 封装用户信息
		UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
		//2. 提交token对象(传递给SecurityManager)
		//2.1 构建主体对象
		Subject subject = SecurityUtils.getSubject();
		//2.2 执行登陆认证
		subject.login(upToken);
		return new JsonResult("login ok!");
	}
}
