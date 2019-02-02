package com.child.controller.admin;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.child.dao.CategoryInfoMapper;
import com.child.model.AdminInfo;
import com.child.model.CategoryInfo;
import com.child.model.CategoryInfoExample;
import com.child.util.Md5Tool;

@Controller
@RequestMapping("/admin/categoryManage")
public class CategoryManageController {

	@Autowired
	private CategoryInfoMapper CategoryInfoMapper;
private String txt="电影分类";
private String txt2="category";
	DecimalFormat df = new DecimalFormat("#.00");
	@RequestMapping("toList")
	public String toList(String flag,ModelMap modelMap) {
		modelMap.addAttribute("flag", flag);
		modelMap.addAttribute("txt", txt);
		modelMap.addAttribute("txt2", txt2);
		return "admin/"+txt2+"Manage";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Object list(ModelMap modelMap,HttpServletRequest request) {
		AdminInfo user = (AdminInfo)request.getSession().getAttribute("User");
		modelMap.addAttribute("User", user);
		CategoryInfoExample te = new CategoryInfoExample();
		CategoryInfoExample.Criteria tc = te.createCriteria();
		List<CategoryInfo> listuser = CategoryInfoMapper.selectByExample(te);
	
		return listuser;
	}
	
	
	@RequestMapping("toAdd")
	public String toAdd(Integer id,ModelMap modelMap) {
		modelMap.addAttribute("txt", txt);
		modelMap.addAttribute("action", "新增");
		modelMap.addAttribute("action2", "save");
		modelMap.addAttribute("txt2", txt2);
		return "admin/add"+txt2;
	}
	
	//插入数据库
	@RequestMapping("save")
	@ResponseBody
	public Map<String,String> save(CategoryInfo sysuser,ModelMap modelMap,HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,String> map = new HashMap<String,String>();
		int result = CategoryInfoMapper.insertSelective(sysuser);
		if(result == 1) {
			map.put("data", "success");
		} else {
			map.put("data", "fail");
		}
		return map;
	}
	
	@RequestMapping("toEdit")
	public String toEditTeacher(Integer id,ModelMap modelMap) {
		modelMap.addAttribute("model", CategoryInfoMapper.selectByPrimaryKey(id));
		modelMap.addAttribute("txt", txt);
		modelMap.addAttribute("action", "修改");
		modelMap.addAttribute("action2", "update");
		modelMap.addAttribute("txt2", txt2);
		return "admin/add"+txt2;
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,String> updateStudent(CategoryInfo sysuser,ModelMap modelMap,HttpServletRequest request) {
	
		Map<String,String> map = new HashMap<String,String>();
		int result = CategoryInfoMapper.updateByPrimaryKeySelective(sysuser);
		if(result == 1) {
			map.put("data", "success");
		} else {
			map.put("data", "fail");
		}
		return map;
	}
	
	
	
	
}
