package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.time.chrono.IsoEra;
import java.util.*;

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
//        System.out.println("登出成功！========================");
//        return "redirect:/back";
        return "views_back/login";
    }
    /**
     *跳转后台首页
     * */
    @RequestMapping("/backIndex.html")
    public String index(){
//        System.out.println("跳转index页面-----------------");
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

            SystemLog systemLog = new SystemLog();
            systemLog.setUserId(adminUser.getUserId());
            systemLog.setOperation("查看个人资料操作");
            systemLog.setLevel(1);
            systemLog.setCreateTime(new Date());
            userService.insertLogByUser(systemLog);

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
    public Object updateUserInfo(@RequestBody User user,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");

        //查看后台获取到的数据
//        System.out.println(user);

        int i=userService.updateBackUserInfo(user);
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("修改个人资料操作");
        systemLog.setLevel(1);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


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
//        System.out.println(oldPassword+"  "+password);

        User adminUser =(User) session.getAttribute("adminUser");

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());


        if (oldPassword==null || password==null){
            systemLog.setOperation("修改密码操作失败,输入原密码为空");
            systemLog.setLevel(2);
            systemLog.setCreateTime(new Date());
            userService.insertLogByUser(systemLog);

            return Result.fail("操作失败",200);

        }



        //如果旧密码和用户的原密码相同
        if (adminUser.getPassword().equals(oldPassword.trim())){

            adminUser.setPassword(password.trim());

            int i=userService.updateBackUserPassword(adminUser);
            systemLog.setOperation("修改密码操作成功");
            systemLog.setLevel(2);
            systemLog.setCreateTime(new Date());
            userService.insertLogByUser(systemLog);

            return Result.success(i,"操作成功",200);

        }

        //如果用户密码和旧密码不同，则修改密码失败
        else {
            systemLog.setOperation("修改密码操作失败,原密码和旧密码不同");
            systemLog.setLevel(2);
            systemLog.setCreateTime(new Date());
            userService.insertLogByUser(systemLog);

            return Result.fail("操作失败",200);

        }

    }


    /**
     *
     * 用户搜索功能
     * 搜索框  获得用户列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/searchBackUserInfo")
    @ResponseBody
    public Object searchBackUserInfo(User user, HttpSession session, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){

        User adminUser =(User) session.getAttribute("adminUser");

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("获取和搜索用户列表操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        //如果是商家,则无权搜索到用户信息
        if (adminUser.getUserRole()!=2){
            return Result.fail("您无权搜索到用户信息",200);
        }

        PageInfo<User> pageInfo = userService.getAllBackUsersBySearchInfo(pageNo, pageSize,user);



        return Result.success(pageInfo);
    }
    /**
     *
     * 后台删除用户信息
     *
     * */
    @RequestMapping("/list/deleteUsersInfo")
    @ResponseBody
    public Object deleteUsersInfo(@RequestBody Integer[] userIds,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");


        int i= userService.removeUserInfosByUserIds(userIds);
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("删除用户信息操作");
        systemLog.setLevel(4);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        return Result.success(i,"操作成功",200);
    }
    /**
     *
     * 后台添加用户
     *
     * */
    @RequestMapping("/list/backAddUser")
    @ResponseBody
    public Object backAddUser(@RequestBody User user,HttpSession session){

        User adminUser =(User) session.getAttribute("adminUser");

//        System.out.println(user.getUserName());
        //先判断数据库中是否已经有了该用户
        boolean userByName=userService.selectUserByName(user.getUserName());

        //如果数据库中不存在此用户
        if (userByName){
            //设置注册的时间
            Date date = new Date();
            user.setRegisterTime(date);
            user.setIsDelete(Byte.valueOf("3"));
            //查看后台获取到的数据
//        System.out.println(user);
            int i=userService.addBackUserInfo(user);

            SystemLog systemLog = new SystemLog();
            systemLog.setUserId(adminUser.getUserId());
            systemLog.setOperation("添加用户操作");
            systemLog.setLevel(4);
            systemLog.setCreateTime(new Date());
            userService.insertLogByUser(systemLog);


            return Result.success(i,"操作成功",200);

        }
        //数据库中存在此用户
        else {

            return Result.fail("操作失败",200);
        }

    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/updateUserToBackPage")
    public String getProduct(ModelMap modelMap,Integer userId){
        //根据Id查询该用户
        User user=userService.selectUserByUserId(userId);
        //把用户放入其中进行显示
        modelMap.put("oldUser",user);

        return "views/list_backUserInfoShowAndUpdate";
    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/showUserDetail")
    public String showProductDetail(ModelMap modelMap,Integer userId,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("查看用户详细信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        //根据Id查询该用户
        User user=userService.selectUserByUserId(userId);
        //把用户放入其中进行显示
        modelMap.put("oldUser",user);

        return "views/list_backUserInfoShowDetail";
    }
    /**
     * 更新用户
     *
     * */
    @RequestMapping("/list/updateUser")
    @ResponseBody
    public Object updateUser(@RequestBody User user,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");

//        System.out.println(user);
        int i=userService.updateBackUserInfo(user);

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("更新用户信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        return Result.success(i,"操作成功",200);
    }
    /**
     * 获得后台用户各个角色的占比
     *
     * */
    @RequestMapping("/list/userPartCount")
    @ResponseBody
    public Object userPartCount(HttpSession session){
        if (session.getAttribute("adminUser")==null){
            return null;
        }
        else{
            HashMap<String, Integer> map = new HashMap<>();

            Integer[] userInfoCount=new Integer[]{0,0,0};
            List<User> users = userService.selectAllUserInfo();
            System.out.println("用户的数量"+users.size());
            for (User user:users){
                //如果该用户是普通用户
                if (user.getUserRole().equals(0)){
                    userInfoCount[0]++;
                }
                else if (user.getUserRole().equals(1)){
                    userInfoCount[1]++;
                }
                else{
                    userInfoCount[2]++;
                }

            }
            System.out.println("现在数组的情况："+ Arrays.toString(userInfoCount));

            map.put("o1",userInfoCount[0]);
            map.put("o2",userInfoCount[1]);
            map.put("o3",userInfoCount[2]);
            return map;
        }
    }

}
