package com.child.controller.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.child.dao.AdminInfoMapper;
import com.child.dao.MovieInfoMapper;
import com.child.dao.MyMoviesMapper;
import com.child.dao.UserInfoMapper;
import com.child.dao.UserMovieReferenceMapper;
import com.child.mathout.bean.MovieInfoMathout;
import com.child.mathout.recommand.MyItemBasedRecommender;
import com.child.mathout.recommand.MySlopeOneRecommender;
import com.child.mathout.recommand.MyUserBasedRecommender;
import com.child.methout.model.GetMovieInfo;
import com.child.model.AdminInfo;
import com.child.model.AdminInfoExample;
import com.child.model.CategoryInfo;
import com.child.model.MovieInfo;
import com.child.model.MovieInfoExample;
import com.child.model.MyMovies;
import com.child.model.MyMoviesExample;
import com.child.model.UserInfo;
import com.child.model.UserInfoExample;
import com.child.model.UserMovieReference;
import com.child.model.UserMovieReferenceExample;

@Controller
@RequestMapping("/user/mymovies")
public class MyMoviesController {
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Autowired
	private MovieInfoMapper movieInfoMapper;
	@Autowired
	private MyMoviesMapper mymoviesMapper;
	
	@Autowired
	private UserMovieReferenceMapper userMovieReferenceMapper;
	
	@RequestMapping("")
	public String home(HttpServletRequest request,Integer type,ModelMap modelMap){
		HttpSession session = request.getSession(); 
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		if(user==null){
			return "user/login";
		}
		modelMap.addAttribute("userInfo", user);
		modelMap.addAttribute("list", getlist(user.getId())) ;
		return "user/movielist";
	}

	private Object getlist(Integer userId) {	
		MyMoviesExample me = new MyMoviesExample();
		MyMoviesExample.Criteria mc = me.createCriteria();
		mc.andUserIdEqualTo(userId);  
		me.setOrderByClause("id desc");//倒叙排序
		List<MyMovies> list = mymoviesMapper.selectByExample(me);
		MovieInfoExample mie = new MovieInfoExample();
		List<MovieInfo> mlist = movieInfoMapper.selectByExample(mie);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		
		for(MovieInfo mi:mlist){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", mi.getId());
			map.put("name", mi.getName());
			map.put("publishYear", mi.getPublishYear());
			map.put("type", mi.getType());
		
			boolean flag=false;
			for(MyMovies mm:list){
				if(mm.getMovieId().equals(mi.getId())){
					flag=true;
					break;
				}
			}
			
			if(flag==true){
				map.put("isReference", 1);
			}else{
				map.put("isReference", 0);
			}
			
			result.add(map); //添加到返回的数据
		}

		  Collections.sort(result, new Comparator<Map<String,Object>>() {  
			  
	            @Override  
	            public int compare(Map<String,Object> o1, Map<String,Object> o2) {  

	                Integer re1 = Integer.parseInt(o1.get("isReference").toString());
	                Integer re2 = Integer.parseInt(o2.get("isReference").toString());
	                if(re1.compareTo(re2)>0){
	                	return -1;
	                }else if(re1.compareTo(re2)==0){
	                	return 0;
	                }else{
	                	return 1;
	                }
	            }  
	        });  
		  result=result.subList(0, 20);
		return result;
	}
	
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String,Object> save(Integer movieId,Integer type,ModelMap modelMap,HttpServletRequest request) {
		HttpSession session = request.getSession(); 
		UserInfo user = (UserInfo) session.getAttribute("userInfo");
		
		Integer userId=user.getId();
		if(type==1){//添加
			UserMovieReference usr = new UserMovieReference();
			usr.setMovieId(movieId);
			usr.setRefrence(5);
			usr.setTimestamp(new Date().getTime());
			usr.setUserId(userId);
			userMovieReferenceMapper.insertSelective(usr);
			MyMovies mm = new MyMovies();
			mm.setMovieId(movieId);
			mm.setUserId(userId);
			mymoviesMapper.insertSelective(mm);
			
		}else if(type==2){//删除
			UserMovieReferenceExample ume = new UserMovieReferenceExample();
			UserMovieReferenceExample.Criteria umc = ume.createCriteria();
			umc.andMovieIdEqualTo(movieId);
			umc.andUserIdEqualTo(userId);
			userMovieReferenceMapper.deleteByExample(ume);
			
			MyMoviesExample mme = new MyMoviesExample();
			MyMoviesExample.Criteria mmc = mme.createCriteria();
			mmc.andUserIdEqualTo(userId);
			mmc.andMovieIdEqualTo(movieId);
			mymoviesMapper.deleteByExample(mme);
			
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("data", "success");
		return result;
	}
	
	

	
	
}
