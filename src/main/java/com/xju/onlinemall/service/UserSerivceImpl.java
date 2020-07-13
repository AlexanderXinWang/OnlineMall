package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.domain.UserExample;
import com.xju.onlinemall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserSerivceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    /**
     * 根据用户名和密码查询用户
     * */
    @Override
    public List<User> selectUserByNameAndPassword(String userName,String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        return users;
    }
}
