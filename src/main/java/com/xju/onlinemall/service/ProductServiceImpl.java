package com.xju.onlinemall.service;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.ProductExample;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.ScatteringByteChannel;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> selectByCategory(Integer categoryId) {
        return productMapper.selectByCategory(categoryId);
    }

    @Override
    public Product selectByProductId(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return  product;
    }


    /**
     *
     *获取对应商户的商品列表信息
     * */

    @Override
    public PageInfo<Product> getAllProductsBypmId(int pageNo,int pageSize,Integer pmId) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPmIdEqualTo(pmId);
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);


        return PageInfo;
    }
    /**
     *
     *根据搜索框条件获得商品信息
     * */
    @Override
    public PageInfo<Product> getAllProductsBypmIdAndSearchInfo(int pageNo, int pageSize, Integer pmId, Product product) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        //设置为查询登录用户的商品
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andPmIdEqualTo(pmId);
        //查看商品是否为空，商品为空则直接跳过，说明不是搜索栏触发的
        //不为空则是搜索栏触发的
        if (product!=null){
            //如果商品id不为空，添加商品id条件
            if (product.getProductId()!=null){
                criteria.andProductIdEqualTo(product.getProductId());
            }
            //如果商品名不为空，添加商品名条件，进行模糊查询
            if (product.getProductName()!=null && product.getProductName().trim().length()>0){
                criteria.andProductNameLike("%"+product.getProductName()+"%");
            }
            //如果商品分类不为空，添加商品分类条件
            if (product.getCategoryId()!=null){
                criteria.andCategoryIdEqualTo(product.getCategoryId());
            }
        }
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }


    /**
     * 商品展示条件筛选方法
     */
    ///////////////////////////////////////////////////////////////////////////
    //  product.html页面方法
    @Override
    public PageInfo<Product> getAllProducts(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectAllProduct();
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByPriceASC(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectByPriceASC();
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByPriceDESC(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectByPriceDESC();
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByRate(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectByRate();
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByTime(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectByTime();
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  product-list无cid筛选方法
    @Override
    public PageInfo<Product> getProductsByPriceRange(int pageNo,int pageSize,double min, double max) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByPriceRangeAndRate(int pageNo, int pageSize, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        PageInfo<Product> PageInfo = new PageInfo<>();
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByPriceRangeAndTime(int pageNo, int pageSize, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        productExample.setOrderByClause("add_time desc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByPriceRangeAndPriceASC(int pageNo, int pageSize, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        productExample.setOrderByClause("price asc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByPriceRangeAndPriceDESC(int pageNo, int pageSize, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        productExample.setOrderByClause("price desc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  product-list有cid筛选方法
    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRange(int pageNo,int pageSize,int categoryId,double min, double max) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCategoryIdEqualTo(categoryId).andCategoryIdEqualTo(categoryId);
        List<Product> list = productMapper.selectByExample(productExample);
        //得到分页器
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndRate(int pageNo, int pageSize, int cid, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        //TO-DO
        PageInfo<Product> PageInfo = new PageInfo<>();
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndTime(int pageNo, int pageSize, int cid, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);
        productExample.setOrderByClause("add_time desc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceASC(int pageNo, int pageSize, int cid, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);

        productExample.setOrderByClause("price asc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceDESC(int pageNo, int pageSize, int cid, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);
        productExample.setOrderByClause("price desc");
        List<Product> list = productMapper.selectByExample(productExample);

        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////

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
