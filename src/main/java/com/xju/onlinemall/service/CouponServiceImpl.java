package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Coupon;
import com.xju.onlinemall.common.domain.CouponExample;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.mapper.CouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public int addCoupons(Coupon coupon) {
        int insert = couponMapper.insert(coupon);
        return insert;
    }

    @Override
    public Coupon selectByCouponId(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return coupon;
    }

    @Override
    public int updateCoupon(Coupon coupon) {
        return couponMapper.updateByPrimaryKeySelective(coupon);
    }

    @Override
    public List<Coupon> selectUsefulCoupons(List<Product> cartProducts) {
        //System.out.println(cartProducts);
        List<Coupon> coupons = new ArrayList<>();
        //System.out.println(coupons);
        for (int i=0;i<cartProducts.size();i++){
            Product product=cartProducts.get(i);
            List<Coupon> addCoupons =couponMapper.selectUsefulCoupons(product.getPmId(),product.getCategoryId(),product.getProductId());
            if (coupons.size()==0){
                //System.out.println("第一步");
                coupons=addCoupons;
                //System.out.println(coupons);
            }else {
                //System.out.println("第二步");
                for (int j=0; j<addCoupons.size();j++){
                    int count =0;
                    Coupon coupon_New =addCoupons.get(j);
                    for (int k=0; k<coupons.size();k++){
                        Coupon coupon_Old =coupons.get(k);
                        if (coupon_New.getCouponId()!=coupon_Old.getCouponId()){
                            count++;
                        }
                    }
                    if (count==coupons.size()){
                        coupons.add(coupon_New);
                    }
                }
            }
        }
        return coupons;
    }
}
