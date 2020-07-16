package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> selectByCategory(String categoryName) {
        return productMapper.selectByCategory(categoryName);
    }

    @Override
    public Product selectByProductId(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return  product;
    }
}
