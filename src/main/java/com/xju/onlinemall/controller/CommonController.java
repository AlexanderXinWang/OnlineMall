package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class CommonController {

    /**
     * 初次访问，判断是否登录，如果没有登录，就跳转登录页面，登录成功时会再次访问此页面设置头部信息
     *
     * 该代码已停用！！！！！！！！！！！！！！！！！！
     * */
//    @RequestMapping("/dddddd")
//    public String index(HttpSession session, Model model){
//        User user =(User) session.getAttribute("user");
//        List cartProducts = (List)session.getAttribute("cartProducts");
//        //如果未登录，跳转登录注册页面
//        if (user==null){
//            //如果当前没有用户登录,设置属性为未登录
//            model.addAttribute("uname","未登录");
//            model.addAttribute("cartProducts","未登录,无法获取到购物车商品信息");
//            return "views_front/my-account";
//        }
//        //如果当前用户存在，跳转首页,设置属性
//        else {
//            String userName = user.getUserName();
//            //如果当前没有用户登录,设置属性为用户名
//            model.addAttribute("uname",userName);
//            model.addAttribute("cartProducts",cartProducts);
//            return "index";
//        }
//        return "index";
//    }
    //测试requset
    @RequestMapping("/testrequest")
    public void testreq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/account.html").forward(request,response);
//        request.getRequestDispatcher("index.html").forward(request,response);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 导航栏header页面跳转请求处理
    // 后续添加session验证,该验证已经实现
    // 对登录页面和注册页面在无登录时可以直接访问
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 跳转登录页面
     * */
    @RequestMapping("/account.html")
    public String account(){
        return "views_front/my-account";
    }

    /**
     * 跳转注册页面
     * */
    @RequestMapping("/register.html")
    public String register(){
        return "views_front/register";
    }

    /**
     *跳转首页
     * */
    @RequestMapping("/index.html")
    public String index(){
        return "index";
    }

    /**
     * 跳转到商品详细页面以及商品评论
     * */
    @RequestMapping("/single-product-simple.html")
    public String singleProduct(){
        return "views_front/single-product-simple";
    }
    /**
     *跳转商品页
     * */
    @RequestMapping("/product.html")
    public String commodity(){
        return "views_front/product";
    }

    /**
     *跳转商品列表页
     * */
    @RequestMapping("/product-list.html")
    public String product_list(){
        return "views_front/product-list";
    }

    /**
     *跳转博客页
     * */
    @RequestMapping("/blog.html")
    public String blog(){
        return "views_front/blog";
    }

    /**
     *跳转关于我们页面
     * */
    @RequestMapping("/about.html")
    public String about(){
        return "views_front/about";
    }

    /**
     *跳转联系我们页面
     * */
    @RequestMapping("/contact.html")
    public String contact(){
        return "views_front/contact";
    }

    /**
     *跳转购物车,并传递用户商品数据
     * */
    @RequestMapping("/cart.html")
    public String cart(HttpSession session, ModelMap modelMap){
        //获取购物车商品内的商品
        List cartProducts = (List)session.getAttribute("cartProducts");
        modelMap.addAttribute("userCartProudcts",cartProducts);
        return "views_front/cart";
    }

    /**
     *跳转结算页面
     * */
    @RequestMapping("/checkout.html")
    public String checkout(){
        return "views_front/checkout";
    }

    /**
     *跳转用户收藏夹
     * */
    @RequestMapping("/wishlist.html")
    public String wishlist(){
        return "views_front/wishlist";
    }

    /**
     *跳转用404页面
     * */
    @RequestMapping("/404.html")
    public String html404(){
        return "404";
    }

    /**
     *跳转项目感言
     * */
    @RequestMapping("/testimonials.html")
    public String testimonials(){
        return "views_front/testimonials";
    }



    /**
     *跳转订单追踪
     * */
    @RequestMapping("/order-tracking.html")
    public String orderTracking(){
        return "views_front/order-tracking";
    }

    /**
     *
     * 跳转相关客户界面
     *
     * */
    @RequestMapping("/client.html")
    public String Client(){
        /**
         *
         * x
         * */
        return "views_front/client.html";
    }
    ///////////////////////////////////////////////////////////////////////////
    // 管理员页面
    ///////////////////////////////////////////////////////////////////////////
    /**
     *跳转管理员登陆
     * */
    @RequestMapping("/adminLogin")
    public String adminLogin(){
        return "views_back/login";
    }
}
