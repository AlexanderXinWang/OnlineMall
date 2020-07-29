package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CouponMapperExtend {
    int logicDeleteByPrimaryKey(@Param("couponId") Integer couponId);

    List<Coupon> selectUsefulCoupons(@Param("pmId") Integer pmId, @Param("categoryId")Integer categoryId, @Param("productId")Integer productId);
}
