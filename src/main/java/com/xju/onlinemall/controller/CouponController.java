package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Coupon;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.CouponService;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class CouponController {
    @Autowired
    CouponService couponService;
    @Autowired
    UserService userService;

    /**
     *
     * 优惠券搜索功能
     * 搜索框  获得优惠券列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/searchCoupons")
    @ResponseBody
    public Object couponListBySearch(Coupon coupon, HttpSession session, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        User adminUser =(User) session.getAttribute("adminUser");
        //将 搜索和获取优惠券信息操作 录入 系统日志
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("搜索和获取优惠券信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        PageInfo<Coupon> pageInfo = couponService.getAllCouponByUserIdAndSearchInfo(pageNo, pageSize,adminUser.getUserId(),coupon);
        return Result.success(pageInfo);
    }

    /**
     *
     * 删除优惠券
     *
     * */
    @RequestMapping("/list/deleteCoupons")
    @ResponseBody
    public Object deleteCoupons(@RequestBody Integer[] couponIds, HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");


        int i = couponService.removeCouponsByCouponIds(couponIds);
        // 将 删除优惠券操作 录入系统日志
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("删除优惠券操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        return Result.success(i,"操作成功",200);
    }
}
