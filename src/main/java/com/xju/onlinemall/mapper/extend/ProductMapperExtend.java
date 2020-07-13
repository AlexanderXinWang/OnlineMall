package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapperExtend {
    List<Product> selectByCategory(@Param("categoryName") String categoryName);
}
