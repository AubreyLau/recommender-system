package com.child.controller.admin;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.child.dao.CategoryInfoMapper;
import com.child.dao.MovieInfoMapper;
import com.child.model.AdminInfo;
import com.child.model.CategoryInfo;
import com.child.model.CategoryInfoExample;
import com.child.model.MovieInfo;
import com.child.model.MovieInfoExample;
import com.child.model.common.CategoryModel;
import com.child.service.impl.Thumbnail;
import com.child.service.impl.Upload;
import com.child.util.Md5Tool;


@Controller
@RequestMapping("/admin/movieManage")
public class VedioManageController {

	  @Autowired      
      private Upload upload;     //上传图片
  @Autowired  
      private Thumbnail thumbnail;  
	@Autowired
	private MovieInfoMapper MovieInfoMapper;
	@Autowired
	private CategoryInfoMapper CategoryInfoMapper;
private String txt="电影";
private String txt2="movie";
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
		MovieInfoExample te = new MovieInfoExample();
		MovieInfoExample.Criteria tc = te.createCriteria();
		te.setOrderByClause("id desc");
		List<MovieInfo> listuser = MovieInfoMapper.selectByExample(te);
		listuser = listuser.subList(0, 300);
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
	
	@RequestMapping("save")
	@ResponseBody
	public Map<String,String> save(MovieInfo sysuser,ModelMap modelMap,HttpServletRequest request) {
		if(sysuser.getType()!=null&&!sysuser.equals("")){
			String type = "";
			String[] split =  sysuser.getType().split(",");
			for(String s :split){
				if(!s.equals("")){
					CategoryInfo c = CategoryInfoMapper.selectByPrimaryKey(Integer.parseInt(s));
					if(c!=null){
						type+=c.getName()+",";
					}
				}
			}
			sysuser.setType(type);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,String> map = new HashMap<String,String>();
		int result = MovieInfoMapper.insertSelective(sysuser);
		if(result == 1) {
			map.put("data", "success");
		} else {
			map.put("data", "fail");
		}
		return map;
	}
	
	@RequestMapping("toEdit")
	public String toEditTeacher(Integer id,ModelMap modelMap) {
		modelMap.addAttribute("model", MovieInfoMapper.selectByPrimaryKey(id));
		modelMap.addAttribute("txt", txt);
		modelMap.addAttribute("action", "修改");
		modelMap.addAttribute("action2", "update");
		modelMap.addAttribute("txt2", txt2);
		return "admin/add"+txt2;
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Map<String,String> updateStudent(MovieInfo sysuser,ModelMap modelMap,HttpServletRequest request) {
		if(sysuser.getType()!=null&&!sysuser.equals("")){
			String type = "";
			String[] split =  sysuser.getType().split(",");
			for(String s :split){
				if(!s.equals("")){
					CategoryInfo c = CategoryInfoMapper.selectByPrimaryKey(Integer.parseInt(s));
					if(c!=null){
						type+=c.getName()+",";
					}
				}
			}
			sysuser.setType(type);
		}
		Map<String,String> map = new HashMap<String,String>();
		int result = MovieInfoMapper.updateByPrimaryKeySelective(sysuser);
		if(result == 1) {
			map.put("data", "success");
		} else {
			map.put("data", "fail");
		}
		return map;
	}
	
	
	  //文件上传并生成缩略图  
    @RequestMapping(value="thumb",method=RequestMethod.POST)  
    @ResponseBody
    public Object GenerateImage(@RequestParam("image")CommonsMultipartFile file,HttpServletRequest request) throws IOException  
    {             
        //根据相对路径获取绝对路径，图片上传后位于元数据中  
        String realUploadPath=request.getServletContext().getRealPath("/")+"images";          
                  
        //获取上传后原图的相对地址  
        String imageUrl=upload.uploadImage(file, realUploadPath);  
                  
        //获取生成的缩略图的相对地址  
        String thumbImageUrl=thumbnail.generateThumbnail(file, realUploadPath);
        Map<String,Object> result =new HashMap<String,Object>();
        result.put("code", 0);
        result.put("url", thumbImageUrl);
     return result;
    }  
	
    
	@RequestMapping("categoryList")
	@ResponseBody
	public Object categoryList(Integer movieId,ModelMap modelMap,HttpServletRequest request) {
		AdminInfo user = (AdminInfo)request.getSession().getAttribute("User");
		modelMap.addAttribute("User", user);
		CategoryInfoExample te = new CategoryInfoExample();
		CategoryInfoExample.Criteria tc = te.createCriteria();
		List<CategoryInfo> listuser = CategoryInfoMapper.selectByExample(te);
		List<CategoryModel> list = new ArrayList<CategoryModel>();
		for(CategoryInfo c:listuser){
			CategoryModel cm = new CategoryModel();
			cm.setId(c.getId());
			cm.setName(c.getName());
			cm.setStatus(0);
		}
		
		if(movieId!=null){
			MovieInfo movie = MovieInfoMapper.selectByPrimaryKey(movieId);
			String type = movie.getType();
			for(CategoryModel m :list){
				if(type.contains(m.getName())){
					m.setStatus(1);
				}
			}
		}
	
	
		return listuser;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,String> deleteNews(String[] ids,ModelMap modelMap) {
		Map<String,String> map = new HashMap<String,String>();
		for (int i = 0,len = ids.length; i < len; i++) {
			MovieInfoMapper.deleteByPrimaryKey(Integer.valueOf(ids[i]));
		}
		map.put("data", "success");
		return map;
	}
	
	
}
