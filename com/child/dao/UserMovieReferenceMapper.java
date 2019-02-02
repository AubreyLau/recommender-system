package com.child.dao;

import com.child.model.UserMovieReference;
import com.child.model.UserMovieReferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMovieReferenceMapper {
    int countByExample(UserMovieReferenceExample example);

    int deleteByExample(UserMovieReferenceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserMovieReference record);

    int insertSelective(UserMovieReference record);

    List<UserMovieReference> selectByExample(UserMovieReferenceExample example);

    UserMovieReference selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserMovieReference record, @Param("example") UserMovieReferenceExample example);

    int updateByExample(@Param("record") UserMovieReference record, @Param("example") UserMovieReferenceExample example);

    int updateByPrimaryKeySelective(UserMovieReference record);

    int updateByPrimaryKey(UserMovieReference record);
}