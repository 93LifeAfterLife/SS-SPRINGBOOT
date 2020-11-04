package com.ss.pj.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class SpringWebFilterConfig {// 取代web.xml中的filter配置
	//===============认证配置================
	/**
	 * 认证-3. 配置过滤器的注册器对象
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean("filterRegistration")
	public FilterRegistrationBean newFilterRegistrationBean() {
		//1. 构建过滤器的注册器对象
		FilterRegistrationBean fBean = new FilterRegistrationBean();
		//2. 注册过滤器对象, 并引入shiroFilterFactory
		DelegatingFilterProxy filter = new DelegatingFilterProxy("shiroFilterFactory");
		fBean.setFilter(filter);
		//3. 配置
		fBean.setEnabled(true);// true表示将生命周期交给spring管理(由ServletContext对象负责), 默认就是true
		fBean.addUrlPatterns("/*");
		return fBean;
	}
}
