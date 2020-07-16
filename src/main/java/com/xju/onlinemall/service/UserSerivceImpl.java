package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.domain.UserExample;
import com.xju.onlinemall.mapper.SystemLogMapper;
import com.xju.onlinemall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserSerivceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    /**
     * 根据用户名和密码查询用户,返回查询结果的列表
     * */
    @Override
    public List<User> selectUserByNameAndPassword(String userName,String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }
    /**
     * 向数据库写入日志信息,注意,用户id是用户表的外键,插入前确保用户id已经在用户表创建!!！
     * */
    @Override
    public void insertLogByUser(SystemLog log) {
        int insert = systemLogMapper.insert(log);
    }

    /**
     * 根据用户对象向数据库插入新用户
     * */
    @Override
    public void register(User user) {
        userMapper.insert(user);
    }


    /**
     * 根据用户id实现更新用户的个人信息
     * */
    @Override
    public void changeAccountDetail(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(user.getUserId());
        userMapper.updateByExampleSelective(user,userExample);
    }

    /**
     * 根据用户id查询用户,返回查询结果的列表
     * */
    @Override
    public List<User> selectUserById(Integer userId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }


}
