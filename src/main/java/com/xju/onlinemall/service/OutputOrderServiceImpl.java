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
//        List<OutputOder> list = outputOderMapper.selectOutputOrdersByUserId(userId);
//        //ArrayList list = outputOderMapper.selectOutputOrdersByUserId(userId);
//        System.out.println(list);

        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);

        OutputOderExample outputOderExample = new OutputOderExample();
        outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId());
        List<OutputOder> outputOderList = outputOderMapper.selectByExample(outputOderExample);
//        System.out.println(outputOderList);
        for (OutputOder outputOder:outputOderList){
            //获得该订单的商品id
            int productId=outputOder.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            product.setSeller(productMapper.selectSellerByProductId(productId));
            outputOder.setProductName(product.getProductName());
            outputOder.setProduct(product);
//            System.out.println(outputOder.getProduct());
        }
//        System.out.println(outputOderList);

        PageInfo<OutputOder> PageInfo = new PageInfo<>(outputOderList);
//        System.out.println(PageInfo);
        return PageInfo;
    }
}
