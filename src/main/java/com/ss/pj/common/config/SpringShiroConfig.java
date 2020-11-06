package com.ss.pj.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ss.pj.sys.service.realm.ShiroUserRealm;

@Configuration
public class SpringShiroConfig {// 取代spring-shiro.xml
	//===============认证授权配置================
	/**
	 * 认证-1. 配置SecurityManager
	 * @return
	 */
	@Bean("securityManager")
	public SecurityManager newShiroSecurityManager(
			ShiroUserRealm realm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 注入Realm
		securityManager.setRealm(realm);
		return securityManager;
	}

	/**
	 * 认证-2. 配置ShiroFilterFactoryBean, 并创建多个filter对象
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sfBean = new ShiroFilterFactoryBean();
		sfBean.setSecurityManager(securityManager);
		// 设置未认证请求的跳转认证页面
		sfBean.setLoginUrl("/doLoginUI");
		sfBean.setUnauthorizedUrl("/");
		
		// 使用此map保存entry的有序性, 设置请求资源的过滤规则
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
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

	//===============授权配置================
	/**
	 * 授权-1. 配置shiro中bean对象的生命周期管理
	 */
	@Bean("shiroLifecycle")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 授权-3. 配置advisor对象, 此对象中的方法将检测要执行的业务方法上
	 * 是否有@RequiresPermissions注解, 有注解则会调用ProxyCreator
	 * 为方法所在类创建代理对象, 然后实现权限控制(AOP)
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
