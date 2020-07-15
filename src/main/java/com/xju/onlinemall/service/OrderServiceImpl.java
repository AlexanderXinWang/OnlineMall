package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.CartMapper;
import com.xju.onlinemall.mapper.OrderItemMapper;
import com.xju.onlinemall.mapper.OrderMapper;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    //注意！该函数返回的是用户的所有订单
    @Override
    public List<Order> getOrderList(Integer userId) {
        //获取所有订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //注意List中的每一个Order都含有一个商品的List,每次循环下来都会产生一一个完整的订单
        for (Order order:orders){
            OrderItemExample orderItemExample = new OrderItemExample();

            orderItemExample.createCriteria().andOrderIdEqualTo(order.getOrderId());
            //获得该订单的所有订单项
            List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
            //获得该订单的全部的商品id列表
            List<Integer> productIdLists=new ArrayList<>();

            //对该订单的订单项遍历,获得全部的商品id

            for (OrderItem orderItem:orderItems){
                productIdLists.add(orderItem.getProductId());

            }
            //获得该订单的所有商品
            List<Product> products=productMapper.selectByProductIdList(productIdLists);
            //设置每个商品的数量
            for (Product product:products){
                int count=0;
                Integer productId = product.getProductId();
                for(Integer single:productIdLists){
                    if (single==productId){
                        count++;
                    }
                }
                product.setCount(count);
            }

            /**
             * 把该订单的商品列表写入该订单对象的商品列表
             * */
            order.setProducts(products);
        }
        return orders;
    }
}
