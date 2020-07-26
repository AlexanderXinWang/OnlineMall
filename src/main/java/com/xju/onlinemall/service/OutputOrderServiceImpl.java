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
    public PageInfo<OutputOder> getAllOutputOrders(int pageNo, int pageSize, Integer userId, boolean isRemoved) {
        PageHelper.startPage(pageNo,pageSize);
        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);
        OutputOderExample outputOderExample = new OutputOderExample();
        if (isRemoved){
            outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("4"));
        }else {
            outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("3"));
        }
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

    @Override
    public int removeOutputOrdersLogicallyByoutIds(Integer... outIds) {
        int count=0;
        int i = 0;
        Integer IS_NOT_DELETE = 3;
        Integer IS_DELETE = 4;
//        System.out.println();
        if (outIds!=null && outIds.length>0){
            for(Integer outId:outIds){
                OutputOder outputOder = outputOderMapper.selectByPrimaryKey(outId);
//                System.out.println(outputOder);
//                OutputOderExample example = new OutputOderExample();
                if (outputOder.getOutIsDelete() == 3){ //逻辑删除
//                    example.createCriteria().andOutIdEqualTo(outputOder.getOutId()).andPmIdEqualTo(outputOder.getPmId()).andOutNumberEqualTo(outputOder.getOutNumber()).andOutDateEqualTo(outputOder.getOutDate()).andProductIdEqualTo(outputOder.getProductId()).andOutStatusEqualTo(outputOder.getOutStatus()).andOutIsDeleteEqualTo(Byte.parseByte(IS_DELETE.toString()));
//                    System.out.println(example);
//                    i = outputOderMapper.updateByExampleSelective(outputOder,example);
//                    System.out.println(i);
                    i = outputOderMapper.removeOutputOrdersLogicallyByoutIds(outId, IS_DELETE);
                    System.out.println(i);
                }else if (outputOder.getOutIsDelete() == 4){ //逻辑恢复
                    i = outputOderMapper.removeOutputOrdersLogicallyByoutIds(outId, IS_NOT_DELETE);
                    System.out.println(i);
                }
                count=count+i;
            }
        }
        return count;
    }

    @Override
    public int updateOutputOrder(OutputOder outputOder) {
        return outputOderMapper.updateByPrimaryKeySelective(outputOder);
    }

    @Override
    public int addOutputOrder(OutputOder outputOder) {
        return outputOderMapper.insertSelective(outputOder);
    }

    @Override
    public Integer getPmIdByUserId(Integer userId) {

        return productManageMapper.selectPmIdByUserId(userId).getPmId();
    }
}
