package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.StarExample;
import com.xju.onlinemall.mapper.ProductMapper;
import com.xju.onlinemall.mapper.StarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class StarServiceImpl implements StarService{
    @Autowired
    StarMapper starMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public PageInfo<Product> findStars(int pageNo, int pageSize,Integer userId) {
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(userId);
        List<Star> stars1 = starMapper.selectByMultiExample(userId);
        System.out.println(stars1);
        List<Product> products = new ArrayList<>();
        for (int i=0;i<stars1.size();i++){
            Product product = productMapper.selectByPrimaryKey(Integer.parseInt(stars1.get(i).getProductId().toString()));
            System.out.println(product);
            products.add(product);
        }
        PageHelper.startPage(pageNo,pageSize);
        return new PageInfo<>(products);
    }

    @Override
    public void addStar(Integer productId, Integer userId) {
        Star star = new Star();
        star.setUserId(userId);
        star.setProductId(productId);
        star.setStarIsDelete(Byte.parseByte("3"));
        starMapper.insertSelective(star);
    }

    @Override
    @Transactional
    public void deleteStarById(Integer starId) {
        starMapper.deleteByPrimaryKey(starId);
    }

    @Override
    public void deleteStarsByIds(List<Integer> ids) {
        starMapper.deleteStarsByIds(ids);
    }

    @Override
    public List<Star> getStarByUserId(Integer userId) {
        return starMapper.selectByMultiExample(userId);
    }

    @Override
    public List<Star> getStarByUserIdAndProductId(Integer userId, Integer productId) {
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(productId);
        return starMapper.selectByExample(starExample);
    }

    @Override
    public void updateByPrimaryKeySelective(Star star) {
        starMapper.updateByPrimaryKeySelective(star);
    }

    @Override
    public List<Star> selectProductIdAndStarIdByPrimaryKey(Integer productId, Integer userId) {
        return starMapper.selectProductIdAndStarIdByPrimaryKey(productId,userId);
    }

    @Override
    public List<Star> selectByMultiExample(Integer userId) {
        return starMapper.selectByMultiExample(userId);
    }
}
