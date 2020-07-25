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
}
