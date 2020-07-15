package com.xju.onlinemall.common.utils;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.util.List;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 这里最好不要乱写代码
         * 因为访问每一个页面,都会先来这里,除了登录和注册页面
         * */
        Object user = request.getSession().getAttribute("user");
        Object cardProducts = request.getSession().getAttribute("cartProducts");
        List<Product> cardProductsList =(List<Product>)cardProducts;
        int cartCount=0;
        if (cardProductsList!=null){
            System.out.println("该用户购物车里有"+cardProductsList.size()+"件商品");
            System.out.println("分别是："+cardProductsList);
            cartCount=cardProductsList.size();
        }else{
            System.out.println("该用户购物车为 空");
        }
//        System.out.println("preHandle----"+user+" ::: "+request.getRequestURL());
        if(user == null) {
            System.out.println("当前无权限,请先登录");
            request.setAttribute("uname", "未登录");
            //这样做会使得每个人页面访问都会设置一遍，浪费性能
            request.setAttribute("cartProducts", "用户未登录,无法获得购物车商品！！！");
            // 获取request返回页面到登录页
            request.getRequestDispatcher("/account.html").forward(request, response);
            return false;
        }
        else {
            //已登录，放行请求
            User u=(User)user;
            //设置当前用户名
            request.setAttribute("uname",u.getUserName());
            //设置当前用户的购物车商品
            //这样做会使得每个人页面访问都会设置一遍，浪费性能
            //request.setAttribute("cartProducts",cardProducts);
            request.setAttribute("cartCount",cartCount);
            /**
             *
             *
             * session里有,不要再添加了
             * request.setAttribute("cardProductsList",cardProductsList);
             * 要不访问每一个页面都要添加一次。 session是常驻的,添加一次可以一直获取,只要你服务器没关或者session没到期
             *         Object cardProducts = request.getSession().getAttribute("cartProducts");
             * 控制里不是写好了吗？
             *
             * @RequestMapping("/cart.html")
             *     public String cart(HttpSession session, ModelMap modelMap){
             *         //获取购物车商品内的商品
             *         ！！！！！！！！！！！！！！！！！！！！！！！在访问cart页面不是已经设置好了吗
             *         List cartProducts = (List)session.getAttribute("cartProducts");
             *         modelMap.addAttribute("cardProductsList",cartProducts);
             *         return "views_front/cart";
             *     }
             * 或者在cart.html页面里直接拿session里的cartProducts
             *
             *
             * */

            return true;
        }
    }

}
