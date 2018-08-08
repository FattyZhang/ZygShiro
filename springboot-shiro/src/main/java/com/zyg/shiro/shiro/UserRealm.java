package com.zyg.shiro.shiro;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
/**
 * realm : 领域的意思
 * 在用户登陆的时候,一般的我们的系统压迫对用户进行 认证和授权2个基本的处理
 * AuthorizingRealm 授权类
 * 实现 doGetAuthorizationInfo(授权),doGetAuthenticationInfo(认证)
 * 在与spring整合项目中，shiro的SecurityManager会自动调用这两个方法，
 * 从而实现认证和授权，可以结合shiro的CacheManager将认证和授权信息保存在缓存中，这样可以提高系统的处理效率。  
 */
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.zyg.shiro.model.User;
import com.zyg.shiro.service.UserService;
/**
 * 实现 doGetAuthorizationInfo(授权),doGetAuthenticationInfo(认证)
 * @author Administrator
 *
 */
public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	/**
	 * 执行授权逻辑...
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		System.out.println("执行授权逻辑...");
		
		//给资源进行授权
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		//获取用户信息
		Subject subject = SecurityUtils.getSubject();
		User uVO = (User)subject.getPrincipal();
		User subUser = userService.selectByPrimaryKey(uVO.getId());
		//获取用户拥有的权限
		String[] tk = subUser.getMobile().split(",");
		List<String > list = Arrays.asList(tk);
		//添加资源授权字符串
		info.addStringPermissions(list);
		return info;
	}
 
	/**
	 * 执行认证逻辑...
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		System.out.println("执行认证逻辑...");
		
		UsernamePasswordToken toKen = (UsernamePasswordToken)arg0;
		
		User uVO = userService.selectByName(toKen.getUsername());
		//验证 user 是否存在 不存在直接返回null;
		//会自动跑出一个 UnknownAccountException 的错误
		if(null == uVO) {
			return null;
		}
		
		//验证 password
		/*
		 * AuthenticationInfo 的子类 传入3个参数 
		 * 1.返回需要返回给login方法的一些数据 也就是 Subject
		 * 2.数据库的密码
		 * 3.shiro的名字
		 */
		
		return new SimpleAuthenticationInfo(uVO,uVO.getPassword(),"");
	}

}
