package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.OrderMapper;
import com.xju.onlinemall.mapper.OutputOderMapper;
import com.xju.onlinemall.mapper.ProductManageMapper;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OutputOrderServiceImpl implements OutputOrderService{
    @Autowired
    private OutputOderMapper outputOderMapper;
    @Autowired
    private ProductManageMapper productManageMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageInfo<OutputOder> getAllOutputOrders(int pageNo, int pageSize, Integer userId, boolean isRemoved, boolean isSended, OutputOder outputOder) {
        PageHelper.startPage(pageNo,pageSize);
        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);
        OutputOderExample outputOderExample = new OutputOderExample();
        if (outputOder==null){
            if (!isSended){
                outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("3")).andOutStatusEqualTo(6);
            }
        }else {
            OutputOderExample.Criteria criteria = outputOderExample.createCriteria();
            criteria.andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("3")).andOutStatusEqualTo(6);
            if (outputOder.getOutId()!=null){
                criteria.andOutIdEqualTo(outputOder.getOutId());
            }
            if (outputOder.getOutNumber()!=null){
                criteria.andOutNumberEqualTo(outputOder.getOutNumber());
            }
        }
        List<OutputOder> outputOderList = outputOderMapper.selectByExample(outputOderExample);
        for (OutputOder opOder:outputOderList){
            //获得该订单的商品id
            int productId=opOder.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            product.setSeller(productMapper.selectSellerByProductId(productId));
            opOder.setProductName(product.getProductName());
            opOder.setProduct(product);
        }
        PageInfo<OutputOder> PageInfo = new PageInfo<>(outputOderList);
        return PageInfo;
    }
    @Override
    public PageInfo<OutputOder> getAllRemovedOutputOrders(int pageNo, int pageSize, Integer userId, boolean isRemoved, boolean isSended, OutputOder outputOder) {
        PageHelper.startPage(pageNo,pageSize);
        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);
        OutputOderExample outputOderExample = new OutputOderExample();
        if (outputOder==null){
            outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("4"));
        }else {
            OutputOderExample.Criteria criteria = outputOderExample.createCriteria();
            criteria.andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("4"));
            if (outputOder.getOutId()!=null){
                criteria.andOutIdEqualTo(outputOder.getOutId());
            }
            if (outputOder.getOutNumber()!=null){
                criteria.andOutNumberEqualTo(outputOder.getOutNumber());
            }
        }
        List<OutputOder> outputOderList = outputOderMapper.selectByExample(outputOderExample);
        for (OutputOder opOder:outputOderList){
            int productId=opOder.getProductId();
            Product product=productMapper.selectByPrimaryKey(productId);
            product.setSeller(productMapper.selectSellerByProductId(productId));
            opOder.setProductName(product.getProductName());
            opOder.setProduct(product);
        }
        PageInfo<OutputOder> PageInfo = new PageInfo<>(outputOderList);
        return PageInfo;    }

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
        if (outIds!=null && outIds.length>0){
            for(Integer outId:outIds){
                OutputOder outputOder = outputOderMapper.selectByPrimaryKey(outId);
                if (outputOder.getOutIsDelete() == 3){ //逻辑删除
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

    @Override
    public int sendOutputOrderByoutId(Integer outId) {
//        出货状态outStatus->7
//        tb_order.output_time添加时间
//        tb_order.status->7
        OutputOder outputOder = outputOderMapper.selectByPrimaryKey(outId);
        outputOder.setOutStatus(7);
        int j = outputOderMapper.updateByPrimaryKeySelective(outputOder);
        Date date = new Date();
        Order order = orderMapper.selectByPrimaryKey(outputOder.getOutNumber());
        order.setOutputTime(date);
        order.setPayStatus(Byte.parseByte("7"));
        int i = orderMapper.updateByPrimaryKeySelective(order);
        if (i==1&&j==1)
            return i;
        else
            return 0;
    }

    @Override
    public int cancelSendOutputOrder(Integer outId) {
        OutputOder outputOder = outputOderMapper.selectByPrimaryKey(outId);
        outputOder.setOutStatus(6);
        int j = outputOderMapper.updateByPrimaryKeySelective(outputOder);
        Order order = orderMapper.selectByPrimaryKey(outputOder.getOutNumber());
        order.setPayStatus(Byte.parseByte("6"));
        int i = orderMapper.updateByPrimaryKeySelective(order);
        int k = orderMapper.setNULLtoOutputTimeByOrderId(order.getOrderId());
        if (i==1&&j==1&&k==1)
            return i;
        else
            return 0;
    }

    @Override
    public PageInfo<OutputOder> getAllSendedOutputOrders(int pageNo, int pageSize, Integer userId, boolean isSended, OutputOder outputOder) {
        PageHelper.startPage(pageNo,pageSize);
        ProductManageExample productManageExample = new ProductManageExample();
        productManageExample.createCriteria().andUserIdEqualTo(userId);
        OutputOderExample outputOderExample = new OutputOderExample();
        if(outputOder==null){
            outputOderExample.createCriteria().andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("3")).andOutStatusEqualTo(7);
        }else{
            OutputOderExample.Criteria criteria = outputOderExample.createCriteria();
            criteria.andPmIdEqualTo(productManageMapper.selectByExample(productManageExample).get(0).getPmId()).andOutIsDeleteEqualTo(Byte.parseByte("3")).andOutStatusEqualTo(7);
            if (outputOder.getOutId()!=null){
                criteria.andOutIdEqualTo(outputOder.getOutId());
            }
            if (outputOder.getOutNumber()!=null){
                criteria.andOutNumberEqualTo(outputOder.getOutNumber());
            }
        }
        List<OutputOder> outputOderList = outputOderMapper.selectByExample(outputOderExample);
        for (OutputOder opOder:outputOderList){
            //获得该订单的商品id
            int productId=opOder.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            product.setSeller(productMapper.selectSellerByProductId(productId));
            opOder.setProductName(product.getProductName());
            opOder.setProduct(product);
        }
        PageInfo<OutputOder> PageInfo = new PageInfo<>(outputOderList);
        return PageInfo;
    }
}
