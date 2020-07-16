package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> selectByCategory(String categoryName);

    Product selectByProductId(Integer productId);

    PageInfo<Product> getAllProducts(int pageNo,int pageSize);
}
