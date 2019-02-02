package com.child.controller.user;

import java.util.ArrayList;
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
import com.child.model.MovieInfo;
import com.child.model.MovieInfoExample;
import com.child.model.MyMovies;
import com.child.model.UserInfo;
import com.child.model.UserInfoExample;

@Controller
@RequestMapping("/user/taste")
public class MovieListController {
	
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
		modelMap.addAttribute("list", getlist(type, user.getId())) ;
		return "user/taste";
	}

	private Object getlist(Integer type,Integer userId) {		
		//推荐电影的List
		List<RecommendedItem> recommendation = null;
		if(type==null){
			type=2;//默认按照基于用户推荐
		}
		if(type==1){//基于用户推荐
			MyUserBasedRecommender mubr = new MyUserBasedRecommender();
			//拿到推荐的电影
			recommendation = mubr.userBasedRecommender(userId,20);
		}else if(type==2){//基于内容推荐
			MyItemBasedRecommender mibr = new MyItemBasedRecommender();
			//拿到推荐的电影
			recommendation = mibr.myItemBasedRecommender(userId,20);
		}else if(type==3){////slope one
			MySlopeOneRecommender msor = new MySlopeOneRecommender();
			//拿到推荐的电影
			recommendation = msor.mySlopeOneRecommender(userId,20);
		}
		//拿到推荐的电影的详细信息
		List<MovieInfoMathout> recommendMovieInfo = new ArrayList<MovieInfoMathout>();
		
	//	List<MyMoviesMathout> recommendMovie = new ArrayList<MyMoviesMathout>();
		
		if(recommendation!=null){
			for(RecommendedItem item:recommendation){
				MovieInfoMathout m = new MovieInfoMathout();
				MovieInfo movie = movieInfoMapper.selectByPrimaryKey(Integer.parseInt(item.getItemID()+""));
				m.setImgUrl(movie.getImgUrl());
				m.setMovieUrl(movie.getMovieUrl());
				m.setName(movie.getName());
				m.setId(movie.getId());
				m.setPreference(item.getValue());
				m.setPublishedYear(movie.getPublishYear());
				m.setType(movie.getType());
				recommendMovieInfo.add(m);
		
				
				
				
				
				
				
			}
		}
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("list", recommendMovieInfo);
		return result;
		
		
		
	}
	
	

	
	
}
