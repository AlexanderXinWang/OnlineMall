package com.xju.onlinemall.common.domain.extend;

import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public class CategoryExtend {
    //属于类别的商品
    private List<Product> list;

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
