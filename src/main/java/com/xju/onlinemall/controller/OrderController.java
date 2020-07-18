package com.xju.onlinemall.controller;


import com.xju.onlinemall.common.domain.Order;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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
        User user =(User) session.getAttribute("user");
        Integer userId = user.getUserId();
        List<Order> orderList = orderService.getOrderList(userId);
        // 把订单数据放入ModelMap
        // 在前端用orderList获取数据
        modelMap.put("orderList",orderList);
        //打印订单商品
        System.out.println(orderList);
        return "views_front/order";
    }
}
