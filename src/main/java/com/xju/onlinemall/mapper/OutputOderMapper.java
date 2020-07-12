package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.OutputOder;
import com.xju.onlinemall.common.domain.OutputOderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OutputOderMapper {
    long countByExample(OutputOderExample example);

    int deleteByExample(OutputOderExample example);

    int deleteByPrimaryKey(Integer outId);

    int insert(OutputOder record);

    int insertSelective(OutputOder record);

    List<OutputOder> selectByExample(OutputOderExample example);

    OutputOder selectByPrimaryKey(Integer outId);

    int updateByExampleSelective(@Param("record") OutputOder record, @Param("example") OutputOderExample example);

    int updateByExample(@Param("record") OutputOder record, @Param("example") OutputOderExample example);

    int updateByPrimaryKeySelective(OutputOder record);

    int updateByPrimaryKey(OutputOder record);
}