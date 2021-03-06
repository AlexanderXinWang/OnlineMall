package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Comment;
import com.xju.onlinemall.common.domain.Order;

import java.util.List;

public interface OrderService {
    /**
     *
     * 传入用户id
     * 获取所有订单
     * 返回的是一个订单列表List<Order>,订单列表包含很多订单
     * List<Order>中的一个order又包含一个List<Product>
     * */
    List<Order> getOrderList(Integer userId);


    /**
     * 确认收货方法
     * */
    String takeDeliveryOfProduct(Integer userId, Integer orderId);

    Order getByOrderId(Integer orderId);

    /**
     * 存储订单
     * */
    String saveOrders(List<Order> orderList);

    String saveOrderComment(Comment comment);
}
