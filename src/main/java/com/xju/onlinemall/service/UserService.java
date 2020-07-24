package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名和密码查询用户
     * */
    public List<User> selectUserByNameAndPassword(String userName, String password);
    /**
     * 根据用户的操作向后台写入登录信息,注意事项看实现类
     * */
    public void insertLogByUser(SystemLog log);

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
}
