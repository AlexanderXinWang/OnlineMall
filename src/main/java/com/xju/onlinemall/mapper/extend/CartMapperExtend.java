package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapperExtend {
    List<Object> selectCartList(@Param("username") String username);

    List<Product> selectMyProductByCartId(@Param("list") List<Integer> productList);

    List<Cart> selectByUserIdAndIsDelete(@Param("userId") Integer userId, @Param("isDelete") Byte isDelete);

    void logicDelete(@Param("userId") Integer userId, @Param("productId") Integer productId);
}
