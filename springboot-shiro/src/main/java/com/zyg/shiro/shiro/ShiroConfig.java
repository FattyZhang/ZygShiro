package com.zyg.shiro.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro 的配置类
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * 用户主体
	 * ShiroFilterFactoryBean
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//添加内置过虑器
		/**
		 * shiro 内置过滤器
		 * 常用的过滤器
		 * anon : 无需认证(登陆)就可以访问
		 * authc : 必须认证才可以访问
		 * user : 如果使用rememberMe可以直接访问
		 * perms : 该资源必须得到资源权限才可以访问
		 * role : 该资源必须得到角色权限才可以访问
		 */
		Map<String ,String> filterMap = new LinkedHashMap<String ,String>();
		// 这里需要注意拦截的 顺序  LinkedHashMap 有序的map
		filterMap.put("/index", "anon");
		filterMap.put("/login", "anon");
		
		//设置需要授权才可以访问的路径 [] 内为授权字符串.在realm内实现授权逻辑
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		
		filterMap.put("/*", "authc");
		
		//修改登陆页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		//未授权跳转的页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}
	
	/**
	 * DefaultWebSecurityManager(安全管理器)
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm realm) {
		DefaultWebSecurityManager securityManager  = new DefaultWebSecurityManager();
		//管理Realm
		securityManager.setRealm(realm);
		return securityManager;
	}
	
	/**
	 * 创建Realm (领域: 执行认证 和授权的逻辑)
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm() {
		return new UserRealm();
	}
	
	/**
	 * 配置ShiroDialect ,用于shiro与thymeleaf的使用
	 */
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
	
	
}
