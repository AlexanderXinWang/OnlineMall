package com.xju.onlinemall.common.domain.extend;

import com.xju.onlinemall.common.domain.Product;

import java.util.List;

/**
 * 订单的扩充,一个订单有一个商品列表
 **/
public class OrderExtend {
    protected List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public OrderExtend setProducts(List<Product> products) {
        this.products = products;
        return this;
    }
}
