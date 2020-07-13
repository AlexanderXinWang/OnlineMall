package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 测试获得AJAX数据能否获得
     * */
    @RequestMapping("/test")
    @ResponseBody
    public Object test(){
        ModelMap modelMap = new ModelMap();
        modelMap.put("ajax","AJAX测试信息,浏览器能否正常显示!");
        return modelMap;
    }
    /**
     * 登出功能
     * */
    @RequestMapping("/logout")
    public String logout(HttpSession session,SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/";
    }
    /**
     * 登录,可以获得AJAX数据
     * */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(String username, String password, HttpSession session){
        ModelMap modelMap = new ModelMap();
        //后台输出请求的用户名和密码
        System.out.println("登录用户名"+username);
        System.out.println("登录用户密码"+password);
        //获得的用户列表信息,默认是只有一个,因为用户名默认是不能重复
        List<User> users = userService.selectUserByNameAndPassword(username, password);
        //如果查询不到用户信息
        if (users.size()==0){
            System.out.println("查询到的用户数量为0");
            //查询结果返回前端
            modelMap.put("success",false);
            return modelMap;
        }
        //查询到用户信息，判断是商家还是用户还是管理员
        else {
            //查询到角色,判断是用户还是管理员
            User user = users.get(0);
            //加入session
            session.setAttribute("user",user);
            modelMap.put("success",true);
            //如果是用户
            if (user.getUserRole()==0){
                //登录成功,请求控制器/,返回主页
                modelMap.put("msg","/");
                return modelMap;
            }
            //如果是商家
           else if (user.getUserRole()==1){
                //修改为后台管理的请求地址
                modelMap.put("msg","/");
                return modelMap;
            }
           //如果是管理员
           else {
                //修改为后台管理的请求地址
                modelMap.put("msg","/");
                return modelMap;
            }
        }
    }

    /*@RequestMapping("/")
    public String register() {
        return "/templates/my-account.html";
    }*/

}
