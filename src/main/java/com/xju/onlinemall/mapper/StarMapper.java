package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.StarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StarMapper {
    long countByExample(StarExample example);

    int deleteByExample(StarExample example);

    int deleteByPrimaryKey(Integer starId);

    int insert(Star record);

    int insertSelective(Star record);

    List<Star> selectByExample(StarExample example);

    Star selectByPrimaryKey(Integer starId);

    int updateByExampleSelective(@Param("record") Star record, @Param("example") StarExample example);

    int updateByExample(@Param("record") Star record, @Param("example") StarExample example);

    int updateByPrimaryKeySelective(Star record);

    int updateByPrimaryKey(Star record);
}