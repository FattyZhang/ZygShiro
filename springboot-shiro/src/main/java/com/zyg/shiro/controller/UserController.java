package com.zyg.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping("index")
	public String test(Model model) {
		System.out.println("index...");
		model.addAttribute("index", "-主页面-");		
		return "index";
	}
	
	@RequestMapping("add")
	public String add(Model model) {
		System.out.println("add...");
		return "user/add";
	}

	@RequestMapping("update")
	public String update(Model model) {
		System.out.println("update...");
		return "user/update";
	}
	
	@RequestMapping("noAuth")
	public String noAuth(Model model) {
		System.out.println("noAuth...");
		return "noAuth";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(Model model) {
		System.out.println("toLogin...");
		return "login1";
	}
	
	@RequestMapping("login")
	public String login(String name,String password,Model model) {
		System.out.println("login....");
		//1.获取Subject
		/*
		 * 接口
		 * 通常我们会将Subject对象理解为一个用户，同样的它也有可能是一个三方程序，
		 * 它是一个抽象的概念，可以理解为任何与系统交互的“东西”都是Subject
		 */
		Subject subject = SecurityUtils.getSubject(); 
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		
		
		//3.执行登陆的方法
		/**
		 * 不报错,即登陆成功
		 */
		try {
			subject.login(token);
			
			//登陆成功
			return "redirect:index";
		} catch (UnknownAccountException e) {
			//UnknownAccountException 错误异常 user不存在
			model.addAttribute("msg", "用户名不存在");
			return "login1";
		}catch (IncorrectCredentialsException e) {
			//IncorrectCredentialsException 密码错误
			model.addAttribute("msg", "密码错误");
			return "login1";
		}
	}
}









