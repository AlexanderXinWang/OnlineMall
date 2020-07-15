package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Admin;
import com.xju.onlinemall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminCotroller {

    @Autowired
    private AdminService adminService;//控制层注入服务层

    @RequestMapping("/***")
    // 接受参数的方式:
//     //直接获取
//    bean 方式获取
//    public String login(@RequestParam("loginName") String name, String password,@RequestParam(name = "age",defaultValue = "0") int age)
    public String login(Admin admin){
        Admin adminInDB = adminService.login(admin);
        if(adminInDB==null){
            System.out.println("用户名不存在");
        }else {
            String passwordInDB = adminInDB.getPassword();
            if(passwordInDB.equals(admin.getPassword())){
                System.out.println("登录成功");
                return "/views_back/login";
            }else{
                System.out.println("登录失败");
            }
        }
        return "/test";
    }
}
