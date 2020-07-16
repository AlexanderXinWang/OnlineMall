package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.ProductExample;
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

    @Override
    public PageInfo<Product> getAllProducts(int pageNo,int pageSize) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);


        return PageInfo;
    }
}
