package com.xju.onlinemall.common.domain.extend;

import com.xju.onlinemall.common.domain.Product;

import java.util.List;

/**
 * 订单的扩充,一个订单有一个商品列表
 **/
public class OrderExtend {
    protected Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "OrderExtend{" +
                "product=" + product +
                '}';
    }
}
