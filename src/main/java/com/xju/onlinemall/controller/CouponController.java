package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Coupon;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.CouponService;
import com.xju.onlinemall.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     *
     * 添加优惠券
     *
     * */
    @RequestMapping("/list/addCoupons")
    @ResponseBody
    public Object addCoupons(@RequestBody Coupon coupon,HttpSession session) throws ParseException {
        User adminUser =(User) session.getAttribute("adminUser");
        //转换的时间,插入到coupon
        //System.out.println(coupon.getcTime()+","+coupon.geteTime());
        DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        coupon.setCreateTime(DateFormat.parse(coupon.getcTime()));
        coupon.setExpireTime(DateFormat.parse(coupon.geteTime()));
        coupon.setCouponIsDelete((byte)3);
        int i = couponService.addCoupons(coupon);

        // 将 添加优惠券操作 录入系统日志
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("添加优惠券操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);
        return Result.success(i,"操作成功",200);
    }
    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/showCouponDetail")
    public String showCouponDetail(ModelMap modelMap, Integer couponId, HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        //根据Id查询该优惠券
        Coupon coupon = couponService.selectByCouponId(couponId);
        //把优惠券放入其中进行显示
        modelMap.put("oldCoupon",coupon);
        //插入日志
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("查看优惠券详细信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        return "views/list-backCouponShowDetail";
    }

    /**
     * 查询对象，回显编辑页面
     *
     * */
    @GetMapping("/list/updateCouponToBackPage")
    public String getCoupon(ModelMap modelMap,Integer couponId){
        ///根据Id查询该优惠券
        Coupon coupon = couponService.selectByCouponId(couponId);
        //把优惠券放入其中进行显示
        modelMap.put("oldCoupon",coupon);

        return "views/list-backCouponShowAndUpdate";
    }

    /**
     * 更新优惠券信息
     *
     * */
    @RequestMapping("/list/updateCoupon")
    @ResponseBody
    public Object updateCoupon(@RequestBody Coupon coupon, HttpSession session) throws ParseException {
        User adminUser =(User) session.getAttribute("adminUser");
        //转换的时间,插入到coupon
        //System.out.println(coupon.getcTime()+","+coupon.geteTime());
        DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        coupon.setCreateTime(DateFormat.parse(coupon.getcTime()));
        coupon.setExpireTime(DateFormat.parse(coupon.geteTime()));
        int i = couponService.updateCoupon(coupon);

        //将 更新优惠券信息操作 录入系统日志
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("更新优惠券信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);
        return Result.success(1,"操作成功",200);
    }

    /**
     * 前台函数
     *
     *
     * 跳转优惠券窗口
     * */
    @RequestMapping("/selectCoupons.html")
    public String coupon(HttpSession session,ModelMap modelMap){
        //从session中获取当前登录用户购物车商品内的商品
        List<Product> cartProducts=new ArrayList<>();
        List<Coupon> coupons=new ArrayList<>();
        if (session.getAttribute("cartProducts")!=null){
            cartProducts = (List<Product>)session.getAttribute("cartProducts");
            coupons = couponService.selectUsefulCoupons(cartProducts);
            System.out.println(coupons);

        }else if (session.getAttribute("cartProducts")==null){
            System.out.println("购物车内没商品");
        }
        //modelMap.addAttribute("cardProductsList",cartProducts);
        return "views_front/coupons";
    }

    /**
     * 跳转优惠明细
     * */
    @RequestMapping("/discountDetails.html")
    public String orderDetail(){

        return "views_front/discount-Details";
    }
}
