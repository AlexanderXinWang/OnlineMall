package com.xju.onlinemall.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.ProductExample;
import com.xju.onlinemall.common.domain.extend.pCountCName;
import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.StarExample;
import com.xju.onlinemall.mapper.ProductMapper;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xju.onlinemall.mapper.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    StarMapper starMapper;

    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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
    public PageInfo<Product> getAllProducts(int pageNo, int pageSize, Integer userId) {
        List<Product> list = productMapper.selectAllProduct();
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByPriceASC(int pageNo, int pageSize, Integer userId) {
        List<Product> list = productMapper.selectByPriceASC();
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByPriceDESC(int pageNo, int pageSize, Integer userId) {
        List<Product> list = productMapper.selectByPriceDESC();
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByRate(int pageNo, int pageSize, Integer userId) {
        List<Product> list = productMapper.selectByRate();
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getAllProductsByTime(int pageNo, int pageSize, Integer userId) {
        List<Product> list = productMapper.selectByTime();
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  product-list无cid筛选方法
    /**
     *停用——————>合并至下方方法中
     */
    /*@Override
    public PageInfo<Product> getProductsByPriceRange(int pageNo,int pageSize,double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        List<Product> list = productMapper.selectByExample(productExample);
        PageInfo<Product> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByPriceRangeAndRate(int pageNo, int pageSize, double min, double max) {
        PageHelper.startPage(pageNo,pageSize);
        List<Product> list = productMapper.selectByPriceRangeAndRate(min,max);
        PageInfo<Product> PageInfo = new PageInfo<>(list);
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
    }*/

    ///////////////////////////////////////////////////////////////////////////
    //  product-list有cid筛选方法
    //
    //————————>与无cid方法合并
    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRange(int pageNo,int pageSize,int cid,double min, double max, Integer userId) {
        ProductExample productExample = new ProductExample();
        if(cid==5) {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        }else {
            productExample.createCriteria().andCategoryIdEqualTo(cid)
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        }
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndRate(int pageNo, int pageSize, int cid, double min, double max, Integer userId) {
        List<Product> list = new ArrayList<>();
        if(cid==5) {
            list = productMapper.selectByPriceRangeAndRate(min,max);
        }else {
            list = productMapper.selectByCategoryAndPriceRangeAndRate(cid,min,max);
        }
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndTime(int pageNo, int pageSize, int cid, double min, double max, Integer userId) {
        ProductExample productExample = new ProductExample();
        if(cid==5) {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        }else {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                    .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);
        }
        productExample.setOrderByClause("add_time desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceASC(int pageNo, int pageSize, int cid, double min, double max, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        }else {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                    .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);
        }
        productExample.setOrderByClause("price asc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> getProductsByCategoryAndPriceRangeAndPriceDESC(int pageNo, int pageSize, int cid, double min, double max, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
        }else {
            productExample.createCriteria().andPriceGreaterThanOrEqualTo(min)
                    .andPriceLessThanOrEqualTo(max).andCategoryIdEqualTo(cid);
        }
        productExample.setOrderByClause("price desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    /**
     * 搜索功能
     */
    ///////////////////////////////////////////////////////////////////////////
    //  header搜索功能——————>跳转到product
    @Override
    public PageInfo<Product> searchProductsByCategory(int pageNo, int pageSize, int cid, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%"));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%"));
        }
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndRate(int pageNo, int pageSize, int cid, String s, Integer userId) {
        //5为全部分类，即查询所有商品
        List<Product> list = new ArrayList<>();
        if (cid==5) {
            list = productMapper.selectBySearchAndRate(s);
        }
        else {
            list = productMapper.selectBySearchAndCategoryAndRate(cid,s);
        }
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndTime(int pageNo, int pageSize, int cid, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%"));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%"));
        }
        productExample.setOrderByClause("add_time desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceASC(int pageNo, int pageSize, int cid, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%"));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%"));
        }
        productExample.setOrderByClause("price asc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceDESC(int pageNo, int pageSize, int cid, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%"));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%");
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%"));
        }
        productExample.setOrderByClause("price desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  product-list
    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceRange(int pageNo, int pageSize, int cid, double min, double max, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceRangeAndRate(int pageNo, int pageSize, int cid, double min, double max, String s, Integer userId) {
        //5为全部分类，即查询所有商品
        List<Product> list = new ArrayList<>();
        if (cid==5) {
            list = productMapper.selectBySearchAndPriceRangeAndRate(s,min,max);
        }
        else {
            list = productMapper.selectBySearchAndCategoryAndPriceRangeAndRate(cid,s,min,max);
        }
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceRangeAndTime(int pageNo, int pageSize, int cid, double min, double max, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        productExample.setOrderByClause("add_time desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceRangeAndPriceASC(int pageNo, int pageSize, int cid, double min, double max, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        productExample.setOrderByClause("price asc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    @Override
    public PageInfo<Product> searchProductsByCategoryAndPriceRangeAndPriceDESC(int pageNo, int pageSize, int cid, double min, double max, String s, Integer userId) {
        ProductExample productExample = new ProductExample();
        if (cid==5) {
            productExample.createCriteria().andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        else {
            productExample.createCriteria().andCategoryIdEqualTo(cid).andProductNameLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max);
            productExample.or(productExample.createCriteria().andCategoryIdEqualTo(cid).andPkeyLike("%"+s+"%")
                    .andPriceGreaterThanOrEqualTo(min).andPriceLessThanOrEqualTo(max));
        }
        productExample.setOrderByClause("price desc");
        List<Product> list = productMapper.selectByExample(productExample);
        List<Product> productList2 = new ArrayList<>();
        for (Product product:list){
            StarExample starExample = new StarExample();
            starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(product.getProductId());
            List<Star> starList = starMapper.selectByExample(starExample);
            if (starList.size()!=0){
                product.setStarId(starList.get(0).getStarId());
                product.setStarIsDelete(starList.get(0).getStarIsDelete());
            }
            productList2.add(product);
        }
        int total = productList2.size();
        if (pageNo==0){
            pageNo=1;
        }
        if (total > pageSize) {
            int toIndex = pageSize * pageNo;
            if (toIndex > total) {
                toIndex = total;
            }
            productList2 = productList2.subList(pageSize * (pageNo - 1), toIndex);
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        page.addAll(productList2);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);
        PageInfo<Product> PageInfo = new PageInfo<>(page);
        return PageInfo;
    }

    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int removeProductsByProductIds(Integer... productIds) {
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

    @Override
    public List<pCountCName> selectAllProductGroupByCategorty(Integer userId) {
        return productMapper.selectNumGroupByCategory(userId);
    }

    @Override
    public List<Product> findNewProducts() throws ParseException {
        String time = "2020-01-01";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        /*try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            logger.error("error");
        } */
//        String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andAddTimeGreaterThan(date);
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<Product> getRecommendByUserId(Integer userId) {
        return productMapper.selectByUserId(userId);
    }

    @Override
    public List<Product> getRelativeByCategory(Integer categoryId) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCategoryIdEqualTo(categoryId);
        productExample.setOrderByClause("price desc");
        return productMapper.selectByExample(productExample);
    }


}
