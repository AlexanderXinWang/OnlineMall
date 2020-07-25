package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.time.chrono.IsoEra;
import java.util.Date;
import java.util.List;

@Controller
public class BackSystemController {
    @Autowired
    UserService userService;
    /**
     * 后台的AJAX请求还是以/list/开始吧
     * 或者在过滤器里添加要过滤的请求
     * 后台登录
     * */
    @RequestMapping("/list/adminLogin")
    @ResponseBody
    public Object adminLogin(HttpSession session,String username, String password){

//        System.out.println("用户名："+username+"密码："+password);
        ModelMap modelMap = new ModelMap();
        //后台输出请求的用户名和密码
        //获得的用户列表信息,默认是只有一个,因为用户名默认是不能重复
        List<User> users = userService.selectUserByNameAndPassword(username, password);
        //如果查询不到用户信息
        if (users.size()==0){
            System.out.println("查询到的用户数量为0");
            //查询结果返回前端
            modelMap.put("success",false);
            modelMap.put("flag",false);
            return modelMap;
        }
        //查询到用户信息，判断是商家还是用户还是管理员，如果是商家或管理员，提示使用后台登录页面进行登录
        else {
            //查询到角色,判断是否是用户,目前管理员只是后台的管理员,对商品或用户信息进行修改
            User user = users.get(0);
            //如果是用户，添加成功信息进JSON,并添加商品信息到session中
            if (user.getUserRole()==1 || user.getUserRole()==2 ){

                //加入session
                session.setAttribute("adminUser",user);
//                session.setAttribute("user",user);
                modelMap.put("success",true);

                //登录成功,请求控制器/,返回主页
                modelMap.put("msg","/backLayout.html");
                //把登录信息写入日志表中
                SystemLog systemLog = new SystemLog();
                systemLog.setUserId(user.getUserId());
                if (user.getUserRole()==1) {
                    systemLog.setOperation("商家登录操作");
                }
                else {
                    systemLog.setOperation("管理员登录操作");
                }
                systemLog.setLevel(2);
                systemLog.setCreateTime(new Date());
                userService.insertLogByUser(systemLog);

                return modelMap;
            }
            //如果是其他用户,返回登录提示信息
            //比如说：该用户是管理员或商家,请进后台登录系统
            //如果是管理员
            else {
                modelMap.put("success",false);
                //修改为后台管理的请求地址
                modelMap.put("msg","您是普通用户，请使用商城登录页面进行登录！");
                modelMap.put("flag",true);
                return modelMap;
            }
        }
    }

    /**
     * 后台用户登出系统
     *
     * */
    @RequestMapping("/backLogout")
    public String logout(HttpSession session, SessionStatus sessionStatus){
        //向数据库插入登出日志
        User user = (User)session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(user.getUserId());
        if (user.getUserRole()==1) {
            systemLog.setOperation("商家登出操作");
        }
        else {
            systemLog.setOperation("管理员登出操作");
        }
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);
        //使session无效并清除,保证用户无法再返回
        session.invalidate();
        sessionStatus.setComplete();
        System.out.println("登出成功！========================");
//        return "redirect:/back";
        return "views_back/login";
    }
    /**
     *跳转后台首页
     * */
    @RequestMapping("/backIndex.html")
    public String index(){
        System.out.println("跳转index页面-----------------");
        return "views_back/login";
    }



    /**
     *
     * 查询后台用户对象，回显到个人资料页面
     *
     * */
    @RequestMapping("/list/updateUserInfoToPage")
    public String updateUserInfoToPage(ModelMap modelMap,HttpSession session){

        User adminUser =(User) session.getAttribute("adminUser");

        if (adminUser==null){
            return "views_back/login";
        }
        else {

            Integer userId = adminUser.getUserId();

            //根据Id查询该user
            User user=userService.getBackInfoById(userId);
            //把用户信息入其中进行显示
            //如果role==1，则是商家，传入角色商家
            if (user.getUserRole()==1){
                modelMap.put("role","商家");
            }
            //如果role==2，测是管理员，传入管理员
            else {
                modelMap.put("role","管理员");
            }
            //把对象放入modelMap用于回显

            modelMap.put("oldUser",user);

//            System.out.println(user);

            return "views/backUserInfo";


        }


    }

    /**
     *
     * 变更用户信息
     *
     * */
    @RequestMapping("/list/updateUserInfo")
    @ResponseBody
    public Object updateUserInfo(@RequestBody User user){
        //查看后台获取到的数据
        System.out.println(user);

        int i=userService.updateBackUserInfo(user);

        return Result.success(i,"操作成功",200);
    }

    /**
     *
     * 变更用户密码
     *
     * */
    @RequestMapping("/list/updateUserPassword")
    @ResponseBody
    public Object updateUserPassword(String oldPassword,String password,HttpSession session){
        //查看后台获取到的数据
        System.out.println(oldPassword+"  "+password);

        User adminUser =(User) session.getAttribute("adminUser");

        if (oldPassword==null || password==null){

            return Result.fail("操作失败",200);

        }

        //如果旧密码和用户的原密码相同
        if (adminUser.getPassword().equals(oldPassword.trim())){

            adminUser.setPassword(password.trim());

            int i=userService.updateBackUserPassword(adminUser);

            return Result.success(i,"操作成功",200);

        }

        //如果用户密码和旧密码不同，则修改密码失败
        else {

            return Result.fail("操作失败",200);

        }

    }
}
