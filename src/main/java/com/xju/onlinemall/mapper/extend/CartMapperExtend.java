package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapperExtend {
    List<Object> selectCartList(@Param("username") String username);
}