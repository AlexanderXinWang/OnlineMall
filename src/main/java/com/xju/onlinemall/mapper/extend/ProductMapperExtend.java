package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapperExtend {
    List<Product> selectByCategory(@Param("categoryName") String categoryName);
    //根据商品id列表进行批量查询
    List<Product> selectByProductIdList(@Param("productIdLists") List<Integer> productIdLists);
}
