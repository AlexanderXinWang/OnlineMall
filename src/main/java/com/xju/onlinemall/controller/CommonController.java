package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CommonController {
    /**
     * 初次访问，判断是否登录，如果没有登录，就跳转登录页面
     * */
    @RequestMapping("/")
    public String index(HttpSession session, Model model){
        User user =(User) session.getAttribute("user");
        if (user==null){
            model.addAttribute("uname","未登录");
            return "my-account";
        }
        else {
            String userName = user.getUserName();
            model.addAttribute("uname",userName);
            return "index";
        }
    }
//    @RequestMapping("/login")
//    public String login(){
//        return "my-account";
//    }
}
