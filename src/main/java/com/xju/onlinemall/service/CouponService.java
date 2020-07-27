package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Coupon;
import com.xju.onlinemall.common.domain.Product;

public interface CouponService {
    PageInfo<Coupon> getAllCouponByUserIdAndSearchInfo(int pageNo, int pageSize, Integer userId, Coupon coupon);

    int removeCouponsByCouponIds(Integer... couponIds);
}
