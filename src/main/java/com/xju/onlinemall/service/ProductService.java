package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.extend.pCountCName;

import java.util.List;

public interface ProductService {

    List<Product> selectByCategory(Integer categoryId);

    Product selectByProductId(Integer productId);

    int removeProductsByProductIds(Integer... productIds);

    int addProduct(Product product);

    int updateProduct(Product product);
    

    /**
     *
     *获取对应商户的商品列表信息
     * */

    PageInfo<Product> getAllProductsBypmId(int pageNo,int pageSize,Integer pmId);
    /**
     *
     *根据搜索框条件获得商品信息
     * */

    PageInfo<Product> getAllProductsBypmIdAndSearchInfo(int pageNo, int pageSize, Integer pmId, Product product);


    /**
     * 商品展示条件筛选方法
     */
    ///////////////////////////////////////////////////////////////////////////
    //  product.html页面方法
    PageInfo<Product> getAllProducts(int pageNo, int pageSize);

    PageInfo<Product> getAllProductsByPriceASC(int pageNo, int pageSize);

    PageInfo<Product> getAllProductsByPriceDESC(int pageNo, int pageSize);

    PageInfo<Product> getAllProductsByRate(int pageNo, int pageSize);

    PageInfo<Product> getAllProductsByTime(int pageNo, int pageSize);

    ///////////////////////////////////////////////////////////////////////////
    //  product-list无cid筛选方法
    /**
     *停用————————>合并至下方方法中
     */
    /*PageInfo<Product> getProductsByPriceRange(int pageNo,int pageSize,double min, double max);

    PageInfo<Product> getProductsByPriceRangeAndRate(int pageNo, int pageSize, double min, double max);

    PageInfo<Product> getProductsByPriceRangeAndTime(int pageNo, int pageSize, double min, double max);

    PageInfo<Product> getProductsByPriceRangeAndPriceASC(int pageNo, int pageSize, double min, double max);

    PageInfo<Product> getProductsByPriceRangeAndPriceDESC(int pageNo, int pageSize, double min, double max);*/

    ///////////////////////////////////////////////////////////////////////////
    //  product-list有cid筛选方法
    PageInfo<Product> getProductsByCategoryAndPriceRange(int pageNo,int pageSize,int categoryId,double min, double max);

    PageInfo<Product> getProductsByCategoryAndPriceRangeAndRate(int pageNo, int pageSize, int cid, double min, double max);

    PageInfo<Product> getProductsByCategoryAndPriceRangeAndTime(int pageNo, int pageSize, int cid, double min, double max);

    PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceASC(int pageNo, int pageSize, int cid, double min, double max);

    PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceDESC(int pageNo, int pageSize, int cid, double min, double max);

    ///////////////////////////////////////////////////////////////////////////
    
    
    /**
     * 搜索框方法
     */
    ///////////////////////////////////////////////////////////////////////////
    //header————>跳转到product
    PageInfo<Product> searchProductsByCategory(int pageNo, int pageSize, int cid, String s);

    PageInfo<Product> searchProductsByCategoryAndRate(int pageNo, int pageSize, int cid, String s);

    PageInfo<Product> searchProductsByCategoryAndTime(int pageNo, int pageSize, int cid, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceASC(int pageNo, int pageSize, int cid, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceDESC(int pageNo, int pageSize, int cid, String s);

    ///////////////////////////////////////////////////////////////////////////
    // product-list
    PageInfo<Product> searchProductsByCategoryAndPriceRange(int pageNo, int pageSize, int cid, double min, double max, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceRangeAndRate(int pageNo, int pageSize, int cid, double min, double max, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceRangeAndTime(int pageNo, int pageSize, int cid, double min, double max, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceRangeAndPriceASC(int pageNo, int pageSize, int cid, double min, double max, String s);

    PageInfo<Product> searchProductsByCategoryAndPriceRangeAndPriceDESC(int pageNo, int pageSize, int cid, double min, double max, String s);

    /**
     * 获得该用户的每个分类的商品数量
     *
     * */
    List<pCountCName> selectAllProductGroupByCategorty(Integer userId);
}
