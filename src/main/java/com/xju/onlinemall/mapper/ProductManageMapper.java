package com.xju.onlinemall.mapper;

import com.xju.onlinemall.common.domain.ProductManage;
import com.xju.onlinemall.common.domain.ProductManageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductManageMapper {
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