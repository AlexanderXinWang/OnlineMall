package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Admin;
import com.xju.onlinemall.common.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名和密码查询用户
     * */
    public List<User> selectUserByNameAndPassword(String userName, String password);
}
