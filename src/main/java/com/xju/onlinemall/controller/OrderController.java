package com.xju.onlinemall.controller;


import com.xju.onlinemall.common.domain.Order;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CartService;
import com.xju.onlinemall.service.OrderService;
import com.xju.onlinemall.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
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
        Double amount=order.getOrderNumber()*order.getProduct().getPrice();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime=simpleDateFormat.format(order.getCreateTime());
        String outTime = null;
        if (order.getOutputTime()!=null){
            outTime=simpleDateFormat.format(order.getOutputTime());
        }
        model.addAttribute("order",order);
        model.addAttribute("createTime",createTime);
        model.addAttribute("outTime",outTime);
        model.addAttribute("amount",amount);
        return "views_front/order-detail";
    }

    /**
     * 商品结算,订单信息插入订单表
     * */
    @RequestMapping("/saveOrders")
    //@ResponseBody
    public String settleAccounts(HttpSession session,HttpServletRequest request,ModelMap modelMap){
        //ModelMap modelMap = new ModelMap();
        //获取当前登录用户信息
        User user=new User();
        user=(User) session.getAttribute("user");
        Integer userId = user.getUserId();//获取用户id
        //从session中获取当前登录用户购物车商品内的商品
        List<Product> cartProducts = (List<Product>)session.getAttribute("cartProducts");
        //收货人
        String receiver =String.valueOf(request.getParameter("billing_name"));
        //地址：国家，城市，行政区，详细地址，邮编，公司/学校
        String billing_country=String.valueOf(request.getParameter("billing_country"));
        String billing_city=String.valueOf(request.getParameter("billing_city"));
        String billing_district=String.valueOf(request.getParameter("billing_district"));
        String billing_adchair=String.valueOf(request.getParameter("billing_adchair"));
        String billing_postcode=String.valueOf(request.getParameter("billing_postcode"));
        String billing_company=String.valueOf(request.getParameter("billing_company"));
        //收货人联系方式
        String phone=String.valueOf(request.getParameter("billing_phone"));
        //订单备注
        String remarks=String.valueOf(request.getParameter("order_comments"));
        //获取结算商品id数组
        String[] productIds=request.getParameterValues("productIdList");
        //获取商品数量
        String[] productNumber=request.getParameterValues("input_productNumber");
        //获取商品总价(实付款)
        String[] payMoney=request.getParameterValues("input_payMoney");
        //初始化商品id
        Integer productId = 0;
        //拼接成地址
        String address = billing_country.concat(" ").
                    concat(billing_city).concat(" ").
                    concat(billing_district).concat(" ").
                    concat(billing_adchair).concat(" ").
                    concat(billing_company).concat(" ").
                    concat("邮编:").
                    concat(billing_postcode);
        //商品数量
        int number = 1; //（暂时默认为1）
        //商品实付款
        Double Money=0.00;
        //下单时间
        Date date=new Date();
        //订单列表
        List<Order> orderList =new ArrayList<Order>();
        System.out.println(receiver);
        System.out.println(address);
        System.out.println(phone);
        System.out.println(remarks);
        for (int i=0;i<productIds.length;i++){
            //System.out.println(productIds[i]);
            String pId=productIds[i];
            if (pId!=null){
                productId=Integer.valueOf(pId);
                System.out.println("商品id"+productId);
            }
            //在缓存中的商品集合里查找商品的对应价格
            for(Object products : cartProducts){
                Product product=(Product)products;
                if (productId==product.getProductId()){
                    number=Integer.valueOf(productNumber[i]);
                    Money=Double.parseDouble(payMoney[i]);
                }
            }
            Order order=new Order();
            order.setUserId(userId);  //下单用户
            order.setProductId(productId);  //下单商品id
            order.setOrderNumber(number);  //商品数量
            order.setPayMoney(Money);  //实付款
            order.setCreateTime(date);  //下单时间
            order.setPayStatus((byte)5);  //订单状态，下单后默认为5（已付款）
            order.setIsDelete((byte)3);  //逻辑删除，默认为3（未删除）
            order.setReceiver(receiver);  //收货人姓名
            order.setReceiverPhone(phone);  //收货人手机号
            order.setAddress(address);  //收货地址
            if (remarks!=null){
                order.setRemarks(remarks);  //订单备注
            }
            System.out.println(order);
            orderList.add(order);
        }
        /**
         * 商品下单后在OrderServiceImpl内调用cartMapper.logicDelete对购物车内商品进行逻辑删除
         */
        String msg =orderService.saveOrders(orderList);
        System.out.println(msg);
        //获取删除操作后的购物车商品信息
        int cartCount=0;//购物车内商品数量
        List<Product> cartListByUserId = cartService.getCartListByUserId(user.getUserId());
        if (cartListByUserId!=null){
            cartCount=cartListByUserId.size();
        }
        //逻辑删除后，更新session中的缓存
        session.setAttribute("cartProducts",cartListByUserId);
        modelMap.addAttribute("headerCartProductList",cartListByUserId);
        modelMap.addAttribute("cartCount",cartCount);
        return "views_front/checkout";
    }
}
