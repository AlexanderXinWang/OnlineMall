package com.xju.onlinemall.common.domain;

import com.xju.onlinemall.common.domain.extend.CategoryExtend;

public class Category extends CategoryExtend{
    private Integer categoryId;

    private String categoryName;

    private Byte isDelete=3;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}