package com.ss.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.pj.common.vo.JsonResult;
import com.ss.pj.common.vo.PageObject;
import com.ss.pj.sys.po.SysLog;
import com.ss.pj.sys.service.SysLogService;

@Controller
@RequestMapping("/log/")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	@RequestMapping("doDeleteObjectById")
	@ResponseBody
	public String doDeleteObjectById(Integer id) {
		int rows = sysLogService.deleteObjectById(id);
		return "delete OK! rows= " + rows;
	}
	
	@RequestMapping("doDeleteObjectsByIds")
	@ResponseBody
	public String doDeleteObjectsByIds(Integer... ids) {
		int rows = sysLogService.deleteObjectsByIds(ids);
		return "delete OK! rows= " + rows;
	}
	
	/** 返回日志列表页面 */
	@RequestMapping("doLogListUI")
	public String doLogListUI() {
		return "sys/log_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(Integer pageCurrent, String username){
		return new JsonResult(sysLogService.findPageObjects(username, pageCurrent));
	}
}
