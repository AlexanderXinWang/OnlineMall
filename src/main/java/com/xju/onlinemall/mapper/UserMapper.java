package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.domain.UserExample;
import java.util.List;

import com.xju.onlinemall.mapper.extend.UserMapperExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends UserMapperExtend {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    void updateByExample(UserExample userExample);
}