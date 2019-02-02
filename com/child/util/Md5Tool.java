package com.child.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
/*
 * JDK里面有一个java.security.MessageDigest类，这个类就是用来加密的。
 * 
 */

public class Md5Tool {

	
	public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		//确定计算方法
		 // 生成一个MD5加密计算摘要
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		//加密后的字符串
		// 计算md5函数
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
		
		
	}
	public static void main(String[] args) {
		try {
			System.out.println(EncoderByMd5("123456"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
