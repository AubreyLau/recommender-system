package com.child.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.child.dao.AdminInfoMapper;
import com.child.dao.CategoryInfoMapper;
import com.child.dao.MovieInfoMapper;
import com.child.dao.UserInfoMapper;
import com.child.dao.UserMovieReferenceMapper;
import com.child.model.AdminInfo;
import com.child.model.AdminInfoExample;
import com.child.model.CategoryInfo;
import com.child.model.CategoryInfoExample;
import com.child.model.MovieInfo;
import com.child.model.MovieInfoExample;
import com.child.model.UserInfo;
import com.child.model.UserInfoExample;
import com.child.model.UserMovieReference;
import com.child.model.UserMovieReferenceExample;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private CategoryInfoMapper CategoryInfoMapper;
	@Autowired
	private MovieInfoMapper movieInfoMapper;
	@Autowired
	private UserMovieReferenceMapper userMovieReferenceMapper;
	
	@RequestMapping("")
	public String login(ModelMap modelMap){
		CategoryInfoExample example = new CategoryInfoExample();
		List<CategoryInfo> list = CategoryInfoMapper.selectByExample(example);
		String rs = "";
		for(CategoryInfo c:list){
			rs+=c.getName()+",";
		}
		rs = rs.substring(0, rs.length()-1);
		modelMap.addAttribute("list", rs);
		return "user/login";
	}

	@RequestMapping("index")
	public String index(ModelMap modelMap,HttpServletRequest request){
		
		return "user/index";
	}

	@RequestMapping("toLogin")
	public String login(UserInfo user,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
			HttpSession session = request.getSession(); 
			UserInfoExample ae = new UserInfoExample();
			UserInfoExample.Criteria ac = ae.createCriteria();
			ac.andEmailEqualTo(user.getEmail());
			ac.andPasswordEqualTo(user.getPassword());
			List<UserInfo> list = userInfoMapper.selectByExample(ae);
		if(list!=null&&list.size()==1) {
				request.getSession().setAttribute("userInfo", list.get(0));
				return "redirect:index.do";
				
		} else {
			
			CategoryInfoExample example = new CategoryInfoExample();
			List<CategoryInfo> clist = CategoryInfoMapper.selectByExample(example);
			String rs = "";
			for(CategoryInfo c:clist){
				rs+=c.getName()+",";
			}
			rs = rs.substring(0, rs.length()-1);
			modelMap.addAttribute("list", rs);
				modelMap.put("", "用户名或密码错误");
				return "user/login";
			
			
		}
	}
	
	
	@RequestMapping("toRegist")
	public String toRegist(UserInfo user,String tagsinput,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		userInfoMapper.insertSelective(user);
		UserInfoExample ue = new UserInfoExample();
		UserInfoExample.Criteria uc = ue.createCriteria();
		uc.andEmailEqualTo(user.getEmail());
		uc.andPasswordEqualTo(user.getPassword());
		List<UserInfo> ulist = userInfoMapper.selectByExample(ue);
		Integer userId = ulist.get(0).getId();
		UserMovieReferenceExample ume = new UserMovieReferenceExample();
		UserMovieReferenceExample.Criteria umc = ume.createCriteria();
		umc.andUserIdEqualTo(userId);
		userMovieReferenceMapper.deleteByExample(ume);
		
		
		
		//根据用户标签存储用户偏好
		Set<MovieInfo> ml = new HashSet<MovieInfo>();
		if(tagsinput!=null&&!tagsinput.equals("")){
			String [] tagsList = tagsinput.split(",");
			for(String tag:tagsList){
				if(tag!=null&&!tag.equals("")){
					MovieInfoExample me = new MovieInfoExample();
					MovieInfoExample.Criteria mc = me.createCriteria();
					mc.andTypeLike("%"+tag+"%");
					List<MovieInfo> mlist = movieInfoMapper.selectByExample(me);
					for(MovieInfo m:mlist){
						ml.add(m);
					}
				}
			}
			
			for(MovieInfo m1:ml){
				UserMovieReference uf = new UserMovieReference();
				uf.setMovieId(m1.getId());
				uf.setRefrence(2);
				uf.setTimestamp(new Date().getTime());
				uf.setUserId(userId);
				userMovieReferenceMapper.insertSelective(uf);
				
				//相当于：insert into uf(MovieID,Refrence,...) values (...);
			}
			
		}
		
		
	// 添加标签
		CategoryInfoExample example = new CategoryInfoExample();
		List<CategoryInfo> list = CategoryInfoMapper.selectByExample(example);
		String rs = "";
		for(CategoryInfo c:list){
			rs+=c.getName()+",";
		}
		rs = rs.substring(0, rs.length()-1);
		modelMap.addAttribute("list", rs);
		return "user/login";
	}
	
	

	
	@RequestMapping("logout")
	public String logout(AdminInfo user,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) {
		request.getSession().removeAttribute("userInfo");
		CategoryInfoExample example = new CategoryInfoExample();
		List<CategoryInfo> list = CategoryInfoMapper.selectByExample(example);
		String rs = "";
		for(CategoryInfo c:list){
			rs+=c.getName()+",";
		}
		rs = rs.substring(0, rs.length()-1);
		modelMap.addAttribute("list", rs);
		return "user/login";
	}
}
