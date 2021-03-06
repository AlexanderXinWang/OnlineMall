package com.xju.onlinemall.controller;


import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CartService;
import com.xju.onlinemall.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    CartService cartService;

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
     * 登出功能,并将该操作时间写入后台日志
     * */
    @RequestMapping("/logout")
    public String logout(HttpSession session,SessionStatus sessionStatus){
        //向数据库插入登出日志
        User user = (User)session.getAttribute("user");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(user.getUserId());
        systemLog.setOperation("登出操作");
        systemLog.setLevel(1);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);
        //使session无效并清除,保证用户无法再返回
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/";
    }
    /**
     * 登录,可以获得AJAX数据,并将该操作写入日志,并且把当前用户的购物车商品信息写入session！！！
     * */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(String username, String password, HttpSession session){
        ModelMap modelMap = new ModelMap();
        //后台输出请求的用户名和密码
        System.out.println("登录用户名:"+username);
        System.out.println("登录用户密码:"+password);
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
            if (user.getUserRole()==0){

                //加入session
                session.setAttribute("user",user);
                modelMap.put("success",true);

                //添加用户的购物车商品列表信息信息进session
                List<Product> cartListByUserId = cartService.getCartListByUserId(user.getUserId());
                session.setAttribute("cartProducts",cartListByUserId);
                //登录成功,请求控制器/,返回主页
                modelMap.put("msg","/index.html");
                //把登录信息写入日志表中
                SystemLog systemLog = new SystemLog();
                systemLog.setUserId(user.getUserId());
                systemLog.setOperation("登录操作");
                systemLog.setLevel(1);
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
                modelMap.put("msg","请使用后台登录页面进行登录！");
                modelMap.put("flag",true);
                return modelMap;
            }
        }
    }

    /*@RequestMapping("/")
    public String register() {
        return "/templates/my-account.html";
    }*/

    /**
     * 注册,可以获得AJAX数据
     * */
    @RequestMapping("/userRegister")
    @ResponseBody
    public Object register(@Param("username") String username, String password,String pwdPay,
                            @Param("phone")String phone) {
        ModelMap modelMap = new ModelMap();
        Byte isDelete = 3;
        System.out.println("测试传入值是否正常："+username+"  "+password+"  "+pwdPay+"  "+phone);
        //传入注册信息
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setPayPassword(pwdPay);
        user.setPhone(phone);
        user.setIsDelete(isDelete);
        user.setRegisterTime(new Date());
        user.setSex(0);
        user.setUserRole(0);

        userService.register(user);

        //注册操作完成后查询数据库看是否成功
        List<User> users = userService.selectUserByNameAndPassword(username, password);
        //如果查询不到用户信息
        if (users.size() == 0) {
            System.out.println("查询到的用户数量为0,注册失败");
            //查询结果返回前端
            modelMap.put("success", false);
        } else {
            System.out.println("注册成功!");
            modelMap.put("success", true);
            modelMap.put("msg", "/account.html");
        }
        return modelMap;
    }


    /**
     * 用户修改个人信息,获得AJAX数据
     * */
    @RequestMapping("/changeDetail")
    @ResponseBody
    public Object changeDetail(HttpSession session,@Param("username") String username, String phone,String email) {
        ModelMap modelMap = new ModelMap();
        User userOrigin = (User)session.getAttribute("user");
        User user = new User();
        Byte isDelete = 3;

        //传入修改信息
        //不修改的原值
        user.setUserId(userOrigin.getUserId());
        user.setUserRole(userOrigin.getUserRole());
        user.setRegisterTime(userOrigin.getRegisterTime());
        user.setIsDelete(userOrigin.getIsDelete());
        user.setImageUrl(userOrigin.getImageUrl());
        user.setPassword(userOrigin.getPassword());
        user.setPayPassword(userOrigin.getPayPassword());

        //修改的信息
        user.setUserName(username);
        /*user.setPassword(password);
        user.setPayPassword(payPassword);*/
        user.setPhone(phone);
        user.setEmail(email);

        userService.changeAccountDetail(user);

        //修改验证
        List<User> users = userService.selectUserById(userOrigin.getUserId());
        if (users.size()==0) {
            System.out.println("查询到的用户数量为0,修改信息失败");
            //查询结果返回前端
            modelMap.put("success", false);
        }
        else {
            System.out.println("修改成功!");
            modelMap.put("success", true);
            modelMap.put("msg", "/logout");
        }
        return modelMap;
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public Object changePassword(HttpSession session,@Param("password") String password, @Param("newPassword")String newPassword,
                                 @Param("payPassword")String payPassword,@Param("newPayPassword")String newPayPassword) {
        ModelMap modelMap = new ModelMap();
        User userOrigin = (User)session.getAttribute("user");

        //修改验证
        if (userOrigin.getPassword().equals(password) && userOrigin.getPayPassword().equals(payPassword)) {
            User user = new User();
            //传入修改信息
            //不修改的原值
            user.setUserId(userOrigin.getUserId());
            user.setUserRole(userOrigin.getUserRole());
            user.setRegisterTime(userOrigin.getRegisterTime());
            user.setIsDelete(userOrigin.getIsDelete());
            user.setImageUrl(userOrigin.getImageUrl());

            //修改的信息
            user.setPassword(newPassword);
            user.setPayPassword(newPayPassword);

            userService.changeAccountDetail(user);
            //查询结果返回前端
            modelMap.put("success", true);
            modelMap.put("msg", "/logout");
        }
        else {
            modelMap.put("success", false);
        }
        return modelMap;
    }
}
