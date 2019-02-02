package com.child.dao;

import com.child.model.CategoryInfo;
import com.child.model.CategoryInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryInfoMapper {
    int countByExample(CategoryInfoExample example);

    int deleteByExample(CategoryInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategoryInfo record);

    int insertSelective(CategoryInfo record);

    List<CategoryInfo> selectByExample(CategoryInfoExample example);

    CategoryInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategoryInfo record, @Param("example") CategoryInfoExample example);

    int updateByExample(@Param("record") CategoryInfo record, @Param("example") CategoryInfoExample example);

    int updateByPrimaryKeySelective(CategoryInfo record);

    int updateByPrimaryKey(CategoryInfo record);
}