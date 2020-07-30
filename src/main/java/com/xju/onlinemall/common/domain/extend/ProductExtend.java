package com.xju.onlinemall.common.domain.extend;

public class ProductExtend {
    private int productNumber;
    private Double payMoney;
    private String seller;
    private Integer starId;
    private Byte starIsDelete;

    @Override
    public String toString() {
        return "ProductExtend{" +
                "productNumber=" + productNumber +
                ", payMoney=" + payMoney +
                ", seller='" + seller + '\'' +
                ", starId=" + starId +
                ", starIsDelete=" + starIsDelete +
                '}';
    }

    public Integer getStarId() {
        return starId;
    }

    public ProductExtend setStarId(Integer starId) {
        this.starId = starId;
        return this;
    }

    public Byte getStarIsDelete() {
        return starIsDelete;
    }

    public ProductExtend setStarIsDelete(Byte starIsDelete) {
        this.starIsDelete = starIsDelete;
        return this;
    }

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

}
