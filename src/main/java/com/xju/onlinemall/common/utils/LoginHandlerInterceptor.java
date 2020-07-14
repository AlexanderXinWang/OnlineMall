package com.xju.onlinemall.common.utils;

import com.xju.onlinemall.common.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginHandlerInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Object user = request.getSession().getAttribute("user");
        Object cardProducts = request.getSession().getAttribute("cartProducts");
//        System.out.println("preHandle----"+user+" ::: "+request.getRequestURL());
        if(user == null) {
            System.out.println("当前无权限,请先登录");
            request.setAttribute("uname","未登录");
            request.setAttribute("cartProducts","无商品！！！");
            // 获取request返回页面到登录页
            //这样做会使得每个人页面访问都会设置一遍，浪费性能
//           request.getRequestDispatcher("/account.html").forward(request,response);
            return false;
            }
        else {
            //已登录，放行请求
            User u=(User)user;
            //设置当前用户名
            request.setAttribute("uname",u.getUserName());
            //设置当前用户的购物车商品
            //这样做会使得每个人页面访问都会设置一遍，浪费性能
//            request.setAttribute("cartProducts",cardProducts);
            return true;
        }
    }

}
