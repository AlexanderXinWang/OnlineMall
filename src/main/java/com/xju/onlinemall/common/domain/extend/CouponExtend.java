package com.xju.onlinemall.common.domain.extend;

public class CouponExtend {
    private String cTime;
    private String eTime;

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

    @Override
    public String toString() {
        return "CouponExtend{" +
                "cTime='" + cTime + '\'' +
                ", eTime='" + eTime + '\'' +
                '}';
    }
}
