package com.xju.onlinemall.common.domain.extend;

public class ProductExtend {
    private int productNumber;
    private Double payMoney;
    private String seller;

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    @Override
    public String toString() {
        return "ProductExtend{" +
                "productNumber=" + productNumber +
                ", payMoney=" + payMoney +
                ", seller='" + seller + '\'' +
                '}';
    }
}
