package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> selectByCategory(Integer categoryId);

    Product selectByProductId(Integer productId);

    List<Product> selectAllProduct();

    PageInfo<Product> getAllProducts(int pageNo,int pageSize);

    int removeProudctsByProductIds(Integer... productIds);

    int addProduct(Product product);
}
