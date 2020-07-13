package com.xju.onlinemall;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class OnlinemallApplicationTests {
    @Autowired
    CategoryMapper mapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    @Test
    public void t1(){
        List<Category> categories = mapper.selectByExample(new CategoryExample());
        System.out.println(categories);
    }
    //测试获取用户
    @Test
    public void t2(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo("user1").andPasswordEqualTo("123");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users.size());
    }
    //测试插入日志信息
    @Test
    public void t3(){
        SystemLog systemLog = new SystemLog();
        //日志中要有id为1的用户
        systemLog.setUserId(1);
        systemLog.setOperation("登录操作");
        systemLog.setLevel(1);
        systemLog.setCreateTime(new Date());
        systemLogMapper.insert(systemLog);
    }
}
