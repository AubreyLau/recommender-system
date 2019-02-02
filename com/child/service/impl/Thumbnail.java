package com.child.service.impl;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service  
public class Thumbnail {  
      
    //设置缩略图的宽度和高度  
    public static final int witdth=100;  
    public static final int heigth=100;  
      
    /*       
     * 生成缩略图并且返回相对地址 
     */  
    public String generateThumbnail(CommonsMultipartFile file,String realUploadPath) throws IOException  
    {  
          
        //如果目录不存在则创建目录  
        File uploadFile=new File(realUploadPath+"/thumbImages");  
        if(!uploadFile.exists()){  
            uploadFile.mkdirs();  
        }     
          
        //缩略图保存的绝对地址  
        String des=realUploadPath+"/thumbImages/"+file.getOriginalFilename();  
        //生成缩略图  
        Thumbnails.of(file.getInputStream()).size(witdth, heigth).toFile(des);  
        //返回缩略图的相对地址  
        return "images/thumbImages/"+file.getOriginalFilename();  
    }  
  
}  