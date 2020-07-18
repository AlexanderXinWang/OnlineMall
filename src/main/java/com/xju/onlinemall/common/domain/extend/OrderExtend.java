package com.xju.onlinemall.common.domain.extend;

import com.xju.onlinemall.common.domain.Product;
import sun.print.PSPrinterJob;

import java.util.List;

/**
 * 订单的扩充,一个订单有一个商品列表
 **/
public class OrderExtend {
    protected Product product;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
                ", status='" + status + '\'' +
                '}';
    }
}
