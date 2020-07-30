package com.xju.onlinemall.common.domain.extend;

public class CouponExtend {
    private String cTime;
    private String eTime;
    private String userName;

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "CouponExtend{" +
                "cTime='" + cTime + '\'' +
                ", eTime='" + eTime + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
