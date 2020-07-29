package com.xju.onlinemall.common.domain;

import com.xju.onlinemall.common.domain.extend.CouponExtend;

import java.util.Date;

public class Coupon extends CouponExtend {
    private Integer couponId;

    private String couponName;

    private Double minAmount;

    private Double couponAmount;

    private Integer userId;

    private Integer categoryId;

    private Integer productId;

    private Date createTime;

    private Date expireTime;

    private Byte couponIsDelete;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName == null ? null : couponName.trim();
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Byte getCouponIsDelete() {
        return couponIsDelete;
    }

    public void setCouponIsDelete(Byte couponIsDelete) {
        this.couponIsDelete = couponIsDelete;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", minAmount=" + minAmount +
                ", couponAmount=" + couponAmount +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", productId=" + productId +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", couponIsDelete=" + couponIsDelete +
                '}';
    }
}