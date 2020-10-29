package com.ss.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.pj.common.vo.JsonResult;
import com.ss.pj.sys.po.SysRole;
import com.ss.pj.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	private SysRoleService sysRoleService;
	
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name, Integer pageCurrent) {
		return new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok!");
	}
	
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		return "sys/role_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole sysRole, Integer... menuIds) {
		sysRoleService.insertObject(sysRole, menuIds);
		return new JsonResult("save ok!");
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysRoleService.findObjectById(id));
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole sysRole, Integer... menuIds) {
		sysRoleService.updateObject(sysRole, menuIds);
		return new JsonResult("update ok!");
	}
	
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles() {
		return new JsonResult(sysRoleService.findObjects());
	}
}
