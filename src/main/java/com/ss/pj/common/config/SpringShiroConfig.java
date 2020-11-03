package com.ss.pj.common.config;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringShiroConfig {// 取代spring-shiro.xml
	/**
	 * 配置SecurityManager
	 * @return
	 */
	@Bean("shiroSecurityManager")
	public SecurityManager newShiroSecurityManager(
			@Autowired Realm realm) {
		DefaultWebSecurityManager sManager = new DefaultWebSecurityManager();
		// 注入Realm
		sManager.setRealm(realm);
		return sManager;
	}

	/**
	 * 配置ShiroFilterFactoryBean, 并创建多个filter对象
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(
			@Qualifier("shiroSecurityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		// 设置未认证请求的跳转认证页面
		sfBean.setLoginUrl("/doLoginUI");
		// 使用此map保存entry的有序性, 设置请求资源的过滤规则
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// anon: 可匿名访问; authc: 需要认证访问
		filterChainDefinitionMap.put("/bower_components/**","anon");
		filterChainDefinitionMap.put("/build/**","anon");
		filterChainDefinitionMap.put("/dist/**","anon");
		filterChainDefinitionMap.put("/plugins/**","anon");
		// 放行登陆操作
		filterChainDefinitionMap.put("/user/doLogin", "anon");
		// 设置登出, 点击链接, 自动跳转登陆
		filterChainDefinitionMap.put("/doLogout", "logout");
		// 除了静态资源和特殊操作外, 其他的全部要进行认证 
		filterChainDefinitionMap.put("/**","authc");
		sfBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return sfBean;
	}
}
