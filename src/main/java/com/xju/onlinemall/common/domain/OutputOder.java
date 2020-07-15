package com.xju.onlinemall.common.domain;

import java.util.Date;

public class OutputOder {
    private Integer outId;

    private Integer pmId;

    private Integer outNumber;

    private Date outDate;

    private Integer outStatus;

    private Byte outIsDelete;

    private String outKeepField;

    private Integer productId;

    public Integer getOutId() {
        return outId;
    }

    public void setOutId(Integer outId) {
        this.outId = outId;
    }

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public Integer getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(Integer outNumber) {
        this.outNumber = outNumber;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Integer getOutStatus() {
        return outStatus;
    }

    public void setOutStatus(Integer outStatus) {
        this.outStatus = outStatus;
    }

    public Byte getOutIsDelete() {
        return outIsDelete;
    }

    public void setOutIsDelete(Byte outIsDelete) {
        this.outIsDelete = outIsDelete;
    }

    public String getOutKeepField() {
        return outKeepField;
    }

    public void setOutKeepField(String outKeepField) {
        this.outKeepField = outKeepField == null ? null : outKeepField.trim();
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "OutputOder{" +
                "outId=" + outId +
                ", pmId=" + pmId +
                ", outNumber=" + outNumber +
                ", outDate=" + outDate +
                ", outStatus=" + outStatus +
                ", outIsDelete=" + outIsDelete +
                ", outKeepField='" + outKeepField + '\'' +
                ", productId=" + productId +
                '}';
    }
}