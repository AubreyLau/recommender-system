package com.child.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.child.dao.AdminInfoMapper;
import com.child.model.AdminInfo;
import com.child.model.AdminInfoExample;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private AdminInfoMapper adminInfoMapper;
	

	
	@RequestMapping("login")
	public String login() {
		return "admin/login";
	}

	@RequestMapping("toLogin")
	public String login(AdminInfo admin,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
			HttpSession session = request.getSession(); //用来存储需要共享的数据
			AdminInfoExample ae = new AdminInfoExample();
			AdminInfoExample.Criteria ac = ae.createCriteria();//Criteria中的方法是定义SQL语句where后的查询条件
			ac.andNameEqualTo(admin.getName());
			ac.andPasswordEqualTo(admin.getPassword());
			List<AdminInfo> list = adminInfoMapper.selectByExample(ae);
			//相当于：select * from user where name = 'admin.getName()' and Password =‘’  
		if(list!=null&&list.size()==1) {
				request.getSession().setAttribute("User", list.get(0));
				return "admin/main";
				
		} else {
			
				modelMap.put("", "用户名或密码错误");
				
				return "admin/login";
			
			
		}
	}
	
	

	
	@RequestMapping("logout")
	public String logout(AdminInfo user,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute("User"); //用来移除HttpSession中的域属性
		return "admin/login";
	}
}
