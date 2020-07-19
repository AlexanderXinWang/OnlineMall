package com.xju.onlinemall.controller;


import com.xju.onlinemall.common.domain.Order;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    /**
     *获取用户订单数据,并放入modelmap中,并跳转订单页面
     * */
    @RequestMapping("/order.html")
    public String order(HttpSession session, ModelMap modelMap){
        //获取当前登录用户信息
        User user =(User) session.getAttribute("user");
        Integer userId = user.getUserId();
        //获取用户订单信息
        List<Order> orderList = orderService.getOrderList(userId);
        // 把订单数据放入ModelMap
        // 在前端用orderList获取数据
        modelMap.put("orderList",orderList);
        //打印订单商品
        //System.out.println(orderList);
        return "views_front/order";
    }

    /**
     * 订单操作：确认收货、追加评价
     * */
    @RequestMapping("/orderOperation")
    public String orderOperation(HttpSession session, HttpServletRequest request,
                                 Integer orderId,String orderStatus,ModelMap modelMap){
        //获取当前登录用户信息
        User user=new User();
        user=(User) session.getAttribute("user");
        Integer userId = user.getUserId();//获取用户id
        //获取前端传值：订单id和订单状态
        orderId=Integer.parseInt(request.getParameter("orderId"));
        orderStatus=String.valueOf(request.getParameter("status"));
        System.out.println("前端传值获取成功"+orderId+","+orderStatus);
        if (orderStatus!="交易成功"){
            String msg=orderService.takeDeliveryOfProduct(userId,orderId);
            System.out.println(msg);
        }
        //重新获取用户订单信息
        List<Order> orderList = orderService.getOrderList(userId);
        // 把订单数据放入ModelMap
        // 在前端用orderList获取数据
        modelMap.put("orderList",orderList);
        return "views_front/order";
    }
}
