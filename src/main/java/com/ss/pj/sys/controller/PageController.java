package com.ss.pj.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
// @Slf4j
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		// log.info("doIndexUI");
		return "starter";
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
}
