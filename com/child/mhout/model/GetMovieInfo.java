/**
 * 获得电影的信息
 */
package com.child.methout.model;

import java.sql.*;
import java.util.*;

import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import com.child.mathout.bean.MovieInfoMathout;
import com.child.mathout.conn.ConnectToMySQL;


public class GetMovieInfo {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//根据推荐的movie的ID，获得movie的详细信息
	public ArrayList<MovieInfoMathout> getMovieByMovieId(int id){
		ArrayList<MovieInfoMathout> movieList = new ArrayList<MovieInfoMathout>();
		try {
			conn = ConnectToMySQL.getConnection();
			String sql = "select m.name,m.published_year,m.type from movies m where m.id="+id+"";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MovieInfoMathout movieInfo = new MovieInfoMathout();
				movieInfo.setName(rs.getString(1));
				movieInfo.setPublishedYear(rs.getString(2));
				movieInfo.setType(rs.getString(3));
				movieInfo.setPreference(5.0);
				movieList.add(movieInfo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return movieList;
	}
	
	//根据推荐的movie的ID，获得movie的详细信息
	public ArrayList<MovieInfoMathout> getMovieByMovieId(List<RecommendedItem> recommendations){
		ArrayList<MovieInfoMathout> movieList = new ArrayList<MovieInfoMathout>();
		try {
			String sql = "";
			conn = ConnectToMySQL.getConnection();
			for(int i=0;i<recommendations.size();i++){
				sql = "select m.name,m.published_year,m.type from movies m where m.id="+recommendations.get(i).getItemID()+"";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()){
					MovieInfoMathout movieInfo = new MovieInfoMathout();
					movieInfo.setName(rs.getString(1));
					movieInfo.setPublishedYear(rs.getString(2));
					movieInfo.setType(rs.getString(3));
					movieInfo.setPreference(recommendations.get(i).getValue());
					movieList.add(movieInfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return movieList;
	}
	
	//根据用户的id，获得用户的所有电影
	public ArrayList<MovieInfoMathout> getMovieByUserId(long userID){
		ArrayList<MovieInfoMathout> movieList = new ArrayList<MovieInfoMathout>();
		try {
			String sql = "select m.name,m.published_year,m.type,mp.preference from movie_preferences mp,movies m where mp.movieID=m.id and mp.userID="+userID+" order by preference desc";
			conn = ConnectToMySQL.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				MovieInfoMathout movieInfo = new MovieInfoMathout();
				movieInfo.setName(rs.getString(1));
				movieInfo.setPublishedYear(rs.getString(2));
				movieInfo.setType(rs.getString(3));
				movieInfo.setPreference(rs.getInt(4));
				movieList.add(movieInfo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return movieList;
	}
	
	//关闭数据库
	public void closeAll(){
		try {
			if(rs !=null){
				rs.close();
				rs = null;
			}
			if(ps !=null){
				ps.close();
				ps = null;
			}
			if(conn !=null){
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
