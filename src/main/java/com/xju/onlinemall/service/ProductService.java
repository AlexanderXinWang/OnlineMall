package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface ProductService {
    PageInfo<Product> selectByCategory(int pageNo,int pageSize,int categoryId);

    Product selectByProductId(Integer productId);

    List<Product> selectAllProduct();

    PageInfo<Product> getAllProducts(int pageNo,int pageSize);

    int removeProudctsByProductIds(Integer... productIds);

    int addProduct(Product product);
}
