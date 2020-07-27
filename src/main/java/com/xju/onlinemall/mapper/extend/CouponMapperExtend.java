package com.xju.onlinemall.mapper.extend;

import org.apache.ibatis.annotations.Param;

public interface CouponMapperExtend {
    int logicDeleteByPrimaryKey(@Param("couponId") Integer couponId);
}
