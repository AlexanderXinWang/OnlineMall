package com.xju.onlinemall.service;

import com.github.pagehelper.IPage;
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
    public PageInfo<Product> getByCategory(int pageNo,int pageSize,int categoryId) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCategoryIdEqualTo(categoryId);
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }


    @Override
    public List<Product> selectByCategory(Integer categoryId) {
        return productMapper.selectByCategory(categoryId);
    }

    @Override
    public Product selectByProductId(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return  product;
    }

    @Override
    public List<Product> selectAllProduct() {
        return productMapper.selectAllProduct();
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

    @Override
    public int removeProudctsByProductIds(Integer... productIds) {
       int count=0;

        if (productIds!=null && productIds.length>0){
            for(Integer id:productIds){
                int i = productMapper.deleteByPrimaryKey(id);
                count=count+i;
            }
        }
        return count;

    }

    @Override
    public int addProduct(Product product) {
        int insert = productMapper.insert(product);
        return insert;
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }
}
