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
        List<Product> list =(List<Product>)cardProducts;
        //购物车中不同商品的数量
        int cartCount=0;
        if (list!=null){
            System.out.println("该用户购物车里有"+list.size()+"件商品");
//            System.out.println("分别是："+list);
            cartCount=list.size();
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
            return true;
        }
    }

}
