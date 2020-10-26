package com.ss.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.pj.common.vo.JsonResult;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.common.vo.SysUserDeptVo;
import com.ss.pj.sys.service.SysUserService;

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
}
