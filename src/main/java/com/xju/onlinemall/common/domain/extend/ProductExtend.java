package com.xju.onlinemall.common.domain.extend;

public class ProductExtend {
    private int productNumber;
    private Double payMoney;

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    @Override
    public String toString() {
        return "ProductExtend{" +
                "productNumber=" + productNumber +
                ", payMoney=" + payMoney +
                '}';
    }
}
