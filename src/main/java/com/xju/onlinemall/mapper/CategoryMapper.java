package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.CategoryExample;
import java.util.List;

import com.xju.onlinemall.mapper.extend.CategoryMapperExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends CategoryMapperExtend {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}