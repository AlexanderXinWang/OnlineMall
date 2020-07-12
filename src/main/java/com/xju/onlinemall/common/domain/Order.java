package com.xju.onlinemall.common.domain;

import java.util.Date;

public class Order {
    private Integer orderId;

    private Integer userId;

    private String orderNumber;

    private Date createTime;

    private Date outputTime;

    private Double payMoney;

    private Byte payState;

    private Byte isDelete;

    private String address;

    private Byte isDeliver;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getOutputTime() {
        return outputTime;
    }

    public void setOutputTime(Date outputTime) {
        this.outputTime = outputTime;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public Byte getPayState() {
        return payState;
    }

    public void setPayState(Byte payState) {
        this.payState = payState;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Byte getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Byte isDeliver) {
        this.isDeliver = isDeliver;
    }
}