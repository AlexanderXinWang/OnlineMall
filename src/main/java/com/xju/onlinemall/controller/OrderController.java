package com.xju.onlinemall.controller;


import com.xju.onlinemall.common.domain.Order;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.OrderService;
import com.xju.onlinemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
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

    @RequestMapping("/order-detail.html")
    public String orderDetail(HttpServletRequest request, Integer orderId, Model model){
        orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getByOrderId(orderId);
        Product product = productService.selectByProductId(order.getProductId());
        model.addAttribute("order",order);
        model.addAttribute("product",product);

        return "views_front/order-detail";
    }

    /**
     * 商品结算,订单信息插入订单表
     * */
    @RequestMapping("/saveOrders")
    public String settleAccounts(HttpServletRequest request, @RequestParam("checkOutList")List<Product> checkOutList){
        String receiver =String.valueOf(request.getParameter("billing_name"));
        String billing_country=String.valueOf(request.getParameter("billing_country"));
        String billing_city=String.valueOf(request.getParameter("billing_city"));
        String billing_district=String.valueOf(request.getParameter("billing_district"));
        String billing_adchair=String.valueOf(request.getParameter("billing_adchair"));
        String billing_postcode=String.valueOf(request.getParameter("billing_postcode"));
        String billing_company=String.valueOf(request.getParameter("billing_company"));
        String billing_phone=String.valueOf(request.getParameter("billing_phone"));
        String order_comments=String.valueOf(request.getParameter("order_comments"));
        //拼接成地址
        String address = billing_country.concat(" ").
                    concat(billing_city).concat(" ").
                    concat(billing_district).concat(" ").
                    concat(billing_adchair).concat(" ").
                    concat(billing_company).concat(" ").
                    concat("邮编:").
                    concat(billing_postcode);
        String phone=billing_phone;
        String remarks=order_comments;
       /* System.out.println(receiver);
        System.out.println(address);
        System.out.println(phone);
        System.out.println(remarks);*/
        System.out.println(checkOutList);
        return "views_front/checkout";
    }
}
