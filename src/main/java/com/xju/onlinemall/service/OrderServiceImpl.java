package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.CartMapper;
import com.xju.onlinemall.mapper.OrderMapper;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;
    //注意！该函数返回的是用户的所有订单
    @Override
    public List<Order> getOrderList(Integer userId) {
        //获取某个用户的所有订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //注意List中的每一个Order都含有一个商品的List,每次循环下来都会产生一一个完整的订单
        for (Order order:orders){
            //获得该订单的商品id
            int productId=order.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            /**
             * 把该订单的商品写入该订单对象的商品列表
             * */
            order.setProduct(product);
            order.setProduct(product);
            //获取订单状态标记
            Byte status_num =order.getPayStatus();
            //获得订单对应状态
            String orderStatus=orderMapper.selectStatusByNum(status_num);
            //将订单状态写入订单对象
            order.setStatus(orderStatus);
            System.out.println(order.getUserId()+","+order.getOrderId()+","+order.getProduct().getProductName()+
                    ",数量×"+order.getOrderNumber()+",实付: ¥"+order.getPayMoney()+","+order.getStatus());
        }
        return orders;
    }

    @Override
    public String takeDeliveryOfProduct(Integer userId, Integer orderId) {
        orderMapper.takeDeliveryOfProduct(userId,orderId);
        return "确认收货成功!";
    }

    @Override
    public Order getByOrderId(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public String saveOrders(List<Order> orderList) {
        for (Object order1:orderList){
            Order order=(Order)order1;
            orderMapper.insert(order);
            //商品下单后对购物车内商品进行逻辑删除
            cartMapper.logicDelete(order.getUserId(),order.getProductId());
        }

        return "下单成功！";
    }
}
