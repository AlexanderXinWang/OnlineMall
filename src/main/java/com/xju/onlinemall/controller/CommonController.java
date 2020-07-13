package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return "views_front/my-account";
        }
        else {
            String userName = user.getUserName();
            model.addAttribute("uname",userName);
            return "index";
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 导航栏header页面跳转请求处理
    ///////////////////////////////////////////////////////////////////////////
    /**
     *跳转首页
     * */
    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    /**
     *跳转商品页
     * */
    @RequestMapping("/commodity.html")
    public String commodity(){
        return "views_front/commodity";
    }

    /**
     *跳转购物车
     * */
    @RequestMapping("/cart.html")
    public String cart(){
        return "views_front/cart";
    }
}
