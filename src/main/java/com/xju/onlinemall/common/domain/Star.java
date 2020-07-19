package com.xju.onlinemall.common.domain;

public class Star {
    private Integer starId;

    private Integer userId;

    private Byte starIsDelete;

    private String starKeepField;

    private Integer productId;

    public Integer getStarId() {
        return starId;
    }

    public void setStarId(Integer starId) {
        this.starId = starId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Byte getStarIsDelete() {
        return starIsDelete;
    }

    public void setStarIsDelete(Byte starIsDelete) {
        this.starIsDelete = starIsDelete;
    }

    public String getStarKeepField() {
        return starKeepField;
    }

    public void setStarKeepField(String starKeepField) {
        this.starKeepField = starKeepField == null ? null : starKeepField.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }



//    @Override
//    public String toString() {
//        return productId.toString();
//    }

    @Override
    public String toString() {
        return "Star{" +
                "starId=" + starId +
                ", userId=" + userId +
                ", starIsDelete=" + starIsDelete +
                ", starKeepField='" + starKeepField + '\'' +
                ", productId=" + productId +
                '}';
    }
}