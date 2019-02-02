package com.child.dao;

import com.child.model.MovieInfo;
import com.child.model.MovieInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieInfoMapper {
    int countByExample(MovieInfoExample example);

    int deleteByExample(MovieInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MovieInfo record);

    int insertSelective(MovieInfo record);

    List<MovieInfo> selectByExample(MovieInfoExample example);

    MovieInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MovieInfo record, @Param("example") MovieInfoExample example);

    int updateByExample(@Param("record") MovieInfo record, @Param("example") MovieInfoExample example);

    int updateByPrimaryKeySelective(MovieInfo record);

    int updateByPrimaryKey(MovieInfo record);
}