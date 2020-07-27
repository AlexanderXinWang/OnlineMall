package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Coupon;
import com.xju.onlinemall.common.domain.CouponExample;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.ProductExample;
import com.xju.onlinemall.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponServiceImpl implements CouponService{
    @Autowired
    CouponMapper couponMapper;

    @Override
    public PageInfo<Coupon> getAllCouponByUserIdAndSearchInfo(int pageNo, int pageSize, Integer userId, Coupon coupon) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        CouponExample couponExample = new CouponExample();
        //设置为查询登录商家的优惠券
        CouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andCouponIsDeleteEqualTo((byte)3);
        //查看优惠券是否为空，优惠券为空则直接跳过，说明不是搜索栏触发的
        //不为空则是搜索栏触发的
        if (coupon!=null){
            //如果优惠券id不为空，添加优惠券id条件
            if (coupon.getCouponId()!=null){
                criteria.andCouponIdEqualTo(coupon.getCouponId());
            }

            //如果优惠券名不为空，添加优惠券名条件，进行模糊查询
            if (coupon.getCouponName()!=null && coupon.getCouponName().trim().length()>0){
                criteria.andCouponNameLike("%"+coupon.getCouponName()+"%");
            }
            //如果优惠商品类别不为空，添加优惠商品类别条件
            if (coupon.getCategoryId()!=null){
                criteria.andCategoryIdEqualTo(coupon.getCategoryId());
            }
            //如果优惠商品id不为空，添加优惠商品id条件
            if (coupon.getProductId()!=null){
                criteria.andProductIdEqualTo(coupon.getProductId());
            }
        }
        List<Coupon> list = couponMapper.selectByExample(couponExample);
        //得到分页器
        PageInfo<Coupon> PageInfo = new PageInfo<>(list);
        return PageInfo;
    }

    @Override
    public int removeCouponsByCouponIds(Integer... couponIds) {
        int count=0;

        if (couponIds!=null && couponIds.length>0){
            for(Integer couponId:couponIds){
                int i = couponMapper.logicDeleteByPrimaryKey(couponId);
                count=count+i;
            }
        }
        return count;
    }
}
