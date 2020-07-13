package com.xju.onlinemall.common.domain;

import java.util.Date;

public class Coupon {
    private Integer couponId;

    private String couponName;

    private String couponInfo;

    private Byte couponIsUsed;

    private Byte couponIsDelete;

    private String couponKeepField;

    private Integer categoryId;

    private Integer productId;

    private Date expireTime;

    private Date createTime;

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

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo == null ? null : couponInfo.trim();
    }

    public Byte getCouponIsUsed() {
        return couponIsUsed;
    }

    public void setCouponIsUsed(Byte couponIsUsed) {
        this.couponIsUsed = couponIsUsed;
    }

    public Byte getCouponIsDelete() {
        return couponIsDelete;
    }

    public void setCouponIsDelete(Byte couponIsDelete) {
        this.couponIsDelete = couponIsDelete;
    }

    public String getCouponKeepField() {
        return couponKeepField;
    }

    public void setCouponKeepField(String couponKeepField) {
        this.couponKeepField = couponKeepField == null ? null : couponKeepField.trim();
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}