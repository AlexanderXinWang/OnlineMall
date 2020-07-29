package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名和密码查询用户
     * */
     List<User> selectUserByNameAndPassword(String userName, String password);
    /**
     * 根据用户的操作向后台写入登录信息,注意事项看实现类
     * */
     void insertLogByUser(SystemLog log);

    void register(User user);

    void changeAccountDetail(User user);

    List<User> selectUserById(Integer userId);

    /**
     * 获取后台用户信息，信息唯一
     *
     * */
    User getBackInfoById(Integer userId);

    /**
     *
     * 更新后台用户信息
     *
     * */
    int updateBackUserInfo(User user);

    /**
     *
     * 修改用户密码
     *
     * */
    int updateBackUserPassword(User adminUser);

    /**
     *
     * 后台获得全部用户信息
     *
     * */
    PageInfo<User> getAllBackUsersBySearchInfo(int pageNo, int pageSize, User user);

    /**
     *
     * 后台删除用户数据
     *
     * */
    int removeUserInfosByUserIds(Integer[] userIds);

    /**
     *
     * 后台添加用户数据
     *
     * */
    int addBackUserInfo(User user);

    /**
     *
     * 查询用户，判断是否已经存在该用户
     *
     * */
    boolean selectUserByName(String userName);

    User selectUserByUserId(Integer userId);

    int updateBackListUserInfo(User user);

    /**
     * 获得全部的用户，用于视图可视化
     * */
    List<User> selectAllUserInfo();
}
