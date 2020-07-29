package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.service.CategoryService;
import com.xju.onlinemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class CommonController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    /**
     *
     * 该处跳转是后台业务的跳转,后台业务均以list开始请求
     *
     * */
    @GetMapping("/page/{views}/{path}")
    public String toPage2(@PathVariable("views") String view,@PathVariable("path") String path,HttpSession session){

        User adminUser = (User)session.getAttribute("adminUser");
        if (adminUser==null){
            return "views_back/login";
        }

        return view+"/"+path;

    }
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
    public String account(HttpSession session){
        List<Category> list=categoryService.list();
        session.setAttribute("categoryList",list);
//        System.out.println(list);
//        System.out.println("跳转account页面---------------------");
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
    public String index(Model model){
//        System.out.println("跳转index页面-----------------");
        List<Product> newProductList = productService.findNewProducts();
        model.addAttribute("newProductList",newProductList);
        return "index";
    }

    /**
     *跳转商品页,并从数据库获取商品信息
     * */
    //——————>ProductController

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    //——————>ProductController

    /**
     *跳转博客页
     * */
    @RequestMapping("/blog.html")
    public String blog(){
        return "views_front/blog";
    }

    /**
     *
     * 跳转关于我们页面
     *
     * */
    @RequestMapping("/about.html")
    public String about(){
        return "views_front/about";
    }
    /**
     *跳转商品360度页面
     * */
    @RequestMapping("/single-product-360deg.html")
    public String productThree(){
        return "views_front/single-product-360deg";
    }

    /**
     *跳转联系我们页面
     * */
    @RequestMapping("/contact.html")
    public String contact(){
        return "views_front/contact";
    }

    /**
     *跳转用户中心,并传递用户数据
     * */
    @RequestMapping("/account-detail.html")
    public String accountDetail(HttpSession session, ModelMap modelMap){
        //获取当前用户对象
        User user =(User) session.getAttribute("user");

        //向页面传递用户参数
        modelMap.addAttribute("password",user.getPassword());
        modelMap.addAttribute("sex",user.getSex());
        modelMap.addAttribute("phone",user.getPhone());
        modelMap.addAttribute("email",user.getEmail());
        modelMap.addAttribute("payPassword",user.getPayPassword());
        return "views_front/account-detail";
    }

    /**
     *跳转购物车,并传递用户商品数据 ——————————————>> CartController
     * */
    /*@RequestMapping("/cart.html")
    public String cart(HttpSession session, ModelMap modelMap){
        //获取购物车商品内的商品
        List cartProducts = (List)session.getAttribute("cartProducts");
        modelMap.addAttribute("cardProductsList",cartProducts);
        return "views_front/cart";
    }*/

    /**
     *跳转结算页面 ——————————————————>> CartController
     * */
    /*@RequestMapping("/checkout.html")
    public String checkout(){
        return "views_front/checkout";
    }*/

    /**
     *跳转用户收藏夹 ——————————————>> StartController
     * */
    /*@RequestMapping("/wishlist.html")
    public String wishlist(){
        return "views_front/wishlist";
    }*/

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
        return "views_front/client";
    }
    ///////////////////////////////////////////////////////////////////////////
    // 管理员页面
    ///////////////////////////////////////////////////////////////////////////
    /**
     *跳转管理员登陆
     * */
    @RequestMapping("/backAdminLogin")
    public String adminLogin(){

        return "views_back/login";
    }
    //访问后台主页

    @RequestMapping("/backLayout.html")
    public String backindex(HttpSession session){
        User adminUser = (User)session.getAttribute("adminUser");
        if (adminUser==null){
            return "views_back/login";
        }
        return "views_back/layout";
    }

}
