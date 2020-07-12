package com.xju.onlinemall.common.domain;

public class ProductManage {
    private Integer pmId;

    private Byte pmIsDelete;

    private Integer userId;

    public Integer getPmId() {
        return pmId;
    }

    public void setPmId(Integer pmId) {
        this.pmId = pmId;
    }

    public Byte getPmIsDelete() {
        return pmIsDelete;
    }

    public void setPmIsDelete(Byte pmIsDelete) {
        this.pmIsDelete = pmIsDelete;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}