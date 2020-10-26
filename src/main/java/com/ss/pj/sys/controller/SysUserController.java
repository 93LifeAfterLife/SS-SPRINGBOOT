package com.ss.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	
}
