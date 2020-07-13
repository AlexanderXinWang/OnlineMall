package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    /**
     * 初次访问，判断是否登录，如果没有登录，就跳转登录页面，登录成功时会再次访问此页面设置头部信息
     * */
    @RequestMapping("/")
    public String index(HttpSession session, Model model){
        User user =(User) session.getAttribute("user");
        //如果未登录，跳转登录注册页面
        if (user==null){
            //如果当前没有用户登录,设置属性为未登录
            model.addAttribute("uname","未登录");
            return "views_front/my-account";
        }
        //如果当前用户存在，跳转首页,设置属性
        else {
            String userName = user.getUserName();
            //如果当前没有用户登录,设置属性为用户名
            model.addAttribute("uname",userName);
            return "index";
        }
    }
//    @RequestMapping("/login")
//    public String login(){
//        return "my-account";
//    }
}
