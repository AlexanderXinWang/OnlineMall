package com.xju.onlinemall.common.domain.extend;

import com.xju.onlinemall.common.domain.Product;

import java.util.Date;

public class OutputOderExtend {
    protected Product product;
    private String productName;

    public Product getProduct() {
        return product;
    }

    public OutputOderExtend setProduct(Product product) {
        this.product = product;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public OutputOderExtend setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    @Override
    public String toString() {
        return "OutputOderExtend{" +
                "product=" + product +
                ", productName='" + productName + '\'' +
                '}';
    }
}
