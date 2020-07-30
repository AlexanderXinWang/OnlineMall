package com.xju.onlinemall.common.domain;

import com.xju.onlinemall.common.domain.extend.ProductExtend;

import java.io.Serializable;
import java.util.Date;

public class Product extends ProductExtend implements Serializable {
    private Integer productId;

    private Integer categoryId;

    private String proNo;

    private String productName;

    private Double price;

    private String pimage;

    private String context;

    private String pkey;

    private Byte status=8;

    private Date addTime;

    private Integer count;

    private Byte isDelete=3;

    private Integer pmId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProNo() {
        return proNo;
    }

    public void setProNo(String proNo) {
        this.proNo = proNo == null ? null : proNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage == null ? null : pimage.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey == null ? null : pkey.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", categoryId=" + categoryId +
                ", proNo='" + proNo + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", pimage='" + pimage + '\'' +
                ", context='" + context + '\'' +
                ", pkey='" + pkey + '\'' +
                ", status=" + status +
                ", addTime=" + addTime +
                ", count=" + count +
                ", isDelete=" + isDelete +
                ", pmId=" + pmId +
                '}';
    }
}