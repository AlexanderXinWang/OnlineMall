package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.*;
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
//        List<User> users = userMapper.selectByExample(userExample);
        return userMapper.selectByExample(userExample);
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
//        List<User> users = userMapper.selectByExample(userExample);
//        return users;
        return userMapper.selectByExample(userExample);
    }

    /**
     *
     * 获取后台用户信息，用于回显页面
     *
     * */
    @Override
    public User getBackInfoById(Integer userId) {

//        User user = userMapper.selectByPrimaryKey(userId);
//        return user;
        return userMapper.selectByPrimaryKey(userId);
    }
    /**
     *
     * 更新后台用户信息的实际操作
     *
     * */

    @Override
    public int updateBackUserInfo(User user) {

//        int i = userMapper.updateByPrimaryKeySelective(user);
//
//        return  i;

        return userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *
     * 修改用户密码
     *
     * */
    @Override
    public int updateBackUserPassword(User adminUser) {

//        int i = userMapper.updateByPrimaryKeySelective(adminUser);
//
//        return i;

        return userMapper.updateByPrimaryKeySelective(adminUser);
    }

    @Override
    public PageInfo<User> getAllBackUsersBySearchInfo(int pageNo, int pageSize, User user) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();


        //查看用户是否为空，用户为空则直接跳过，说明不是搜索栏触发的
        //不为空则是搜索栏触发的
        if (user!=null){
            //如果商品id不为空，添加商品id条件
            if (user.getUserId()!=null){
                criteria.andUserIdEqualTo(user.getUserId());
            }
            //如果商品名不为空，添加商品名条件，进行模糊查询
            if (user.getUserName()!=null && user.getUserName().trim().length()>0){
                criteria.andUserNameLike("%"+user.getUserName()+"%");
            }
            //如果商品分类不为空，添加商品分类条件
            if (user.getUserRole()!=null){
                criteria.andUserRoleEqualTo(user.getUserRole());
            }
        }
        List<User> users = userMapper.selectByExample(userExample);
        //得到分页器
        PageInfo<User> PageInfo = new PageInfo<>(users);
        return PageInfo;
    }
}
