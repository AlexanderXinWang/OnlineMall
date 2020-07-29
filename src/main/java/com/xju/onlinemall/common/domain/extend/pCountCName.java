package com.xju.onlinemall.common.domain.extend;

public class pCountCName {
    private Integer count;
    private String categoryName;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "pCountCName{" +
                "count=" + count +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
