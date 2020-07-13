package com.xju.onlinemall;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.AdminMapper;
import com.xju.onlinemall.mapper.BookMapper;
import com.xju.onlinemall.mapper.CategoryMapper;
import com.xju.onlinemall.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnlinemallApplicationTests {
    @Autowired
    CategoryMapper mapper;
    @Autowired
    UserMapper userMapper;
    @Test
    public void t1(){
        List<Category> categories = mapper.selectByExample(new CategoryExample());
        System.out.println(categories);
    }
    //测试获取用户
    @Test
    public void t2(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo("userName").andPasswordEqualTo("password");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users.size());
    }
}
