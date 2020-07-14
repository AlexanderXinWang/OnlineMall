package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface CartService {
    /**
     * 根据用户名查找购物车,查询的结果依然是Cart
     * */
    List<Object> list(String username);
    /**
     * 根据用户id获得购物车中的商品
     * */
    List<Product> getCartListByUserId(Integer userId);
    /**
     * 向购物车中添加商品
     * */
    boolean insertIntoCartByProdcutId(Integer userId,Integer prodectId);
}
