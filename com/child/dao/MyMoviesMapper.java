package com.child.dao;

import com.child.model.MyMovies;
import com.child.model.MyMoviesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyMoviesMapper {
    int countByExample(MyMoviesExample example);

    int deleteByExample(MyMoviesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MyMovies record);

    int insertSelective(MyMovies record);

    List<MyMovies> selectByExample(MyMoviesExample example);

    MyMovies selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MyMovies record, @Param("example") MyMoviesExample example);

    int updateByExample(@Param("record") MyMovies record, @Param("example") MyMoviesExample example);

    int updateByPrimaryKeySelective(MyMovies record);

    int updateByPrimaryKey(MyMovies record);
}