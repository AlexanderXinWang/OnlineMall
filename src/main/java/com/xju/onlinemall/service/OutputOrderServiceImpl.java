package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.OutputOderMapper;
import com.xju.onlinemall.mapper.ProductManageMapper;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OutputOrderServiceImpl implements OutputOrderService{
    @Autowired
    private OutputOderMapper outputOderMapper;
    @Autowired
    private ProductManageMapper productManageMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfo<OutputOder> getAllOutputOrders(int pageNo, int pageSize, Integer userId) {
        PageHelper.startPage(pageNo,pageSize);
        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);
        OutputOderExample outputOderExample = new OutputOderExample();
        outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId());
        List<OutputOder> outputOderList = outputOderMapper.selectByExample(outputOderExample);
        for (OutputOder outputOder:outputOderList){
            //获得该订单的商品id
            int productId=outputOder.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            product.setSeller(productMapper.selectSellerByProductId(productId));
            outputOder.setProductName(product.getProductName());
            outputOder.setProduct(product);
        }
        PageInfo<OutputOder> PageInfo = new PageInfo<>(outputOderList);
        return PageInfo;
    }

    @Override
    public OutputOder selectOneOutputOrderByOutId(Integer outId) {
        OutputOder outputOder = outputOderMapper.selectByPrimaryKey(outId);
        outputOder.setProductName(productMapper.selectByPrimaryKey(outputOder.getProductId()).getProductName());
        outputOder.setProduct(productMapper.selectByPrimaryKey(outputOder.getProductId()));
        return outputOder;
    }
}
