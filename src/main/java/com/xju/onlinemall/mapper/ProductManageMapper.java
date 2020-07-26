package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.ProductManage;
import com.xju.onlinemall.common.domain.ProductManageExample;
import java.util.List;

import com.xju.onlinemall.mapper.extend.ProductManageMapperExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductManageMapper extends ProductManageMapperExtend {
    long countByExample(ProductManageExample example);

    int deleteByExample(ProductManageExample example);

    int deleteByPrimaryKey(Integer pmId);

    int insert(ProductManage record);

    int insertSelective(ProductManage record);

    List<ProductManage> selectByExample(ProductManageExample example);

    ProductManage selectByPrimaryKey(Integer pmId);

    int updateByExampleSelective(@Param("record") ProductManage record, @Param("example") ProductManageExample example);

    int updateByExample(@Param("record") ProductManage record, @Param("example") ProductManageExample example);

    int updateByPrimaryKeySelective(ProductManage record);

    int updateByPrimaryKey(ProductManage record);
}