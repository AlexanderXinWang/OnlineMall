package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.OutputOder;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.OutputOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class OutputOrderController {
    @Autowired
    private OutputOrderService outputOrderService;

    @RequestMapping("/list/outputOrders")
    @ResponseBody
    public Object ShowOutputOrderList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId, HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<OutputOder> pageInfo=null;
        boolean isRemoved = false;
        pageInfo = outputOrderService.getAllOutputOrders(pageNo, pageSize, adminUser.getUserId(), isRemoved);
        return Result.success(pageInfo);
    }
    @RequestMapping("/list/removedoutputOrders")
    @ResponseBody
    public Object ShowRemovedOutputOrderList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId, HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<OutputOder> pageInfo=null;
        boolean isRemoved = true;
        pageInfo = outputOrderService.getAllOutputOrders(pageNo, pageSize, adminUser.getUserId(), isRemoved);
        return Result.success(pageInfo);
    }

    @GetMapping("/list/showOutputOrderDetail")
    public String showCategoryDetail(ModelMap modelMap,Integer outId){
        //根据Id查询该商品
        OutputOder outputOder= outputOrderService.selectOneOutputOrderByOutId(outId);
        //把商品放入其中进行显示
        modelMap.put("outputOder",outputOder);
        return "views/list-backOutputOrdersShowDetail";
    }

    @RequestMapping("/list/deleteOutputOrders")
    @ResponseBody
    public Object deleteOutputOrders(@RequestBody Integer[] outIds){
        int i = outputOrderService.removeOutputOrdersLogicallyByoutIds(outIds);
        return Result.success(i,"操作成功",200);
    }

    @GetMapping("/list/updateOutputOrderInfoByoutId")
    public String updateOutputOrderInfoByoutId(ModelMap modelMap,Integer outId){
        OutputOder outputOder = outputOrderService.selectOneOutputOrderByOutId(outId);
        modelMap.put("outputOrderInfo",outputOder);
        return "views/list-backOutputOrderShowAndUpdate";
    }

    @RequestMapping("/list/updateOutputOrder")
    @ResponseBody
    public Object updateCategory(@RequestBody OutputOder outputOder){
        int i = outputOrderService.updateOutputOrder(outputOder);
        return Result.success(i,"操作成功",200);
    }

    @RequestMapping("/list/addOutputOrder")
    @ResponseBody
    public Object addOutputOrder(@RequestBody OutputOder outputOder, HttpSession session){
        //设置添加的时间
        Date date = new Date();
        outputOder.setOutDate(date);
        User adminUser =(User) session.getAttribute("adminUser");
        Integer pmId = outputOrderService.getPmIdByUserId(adminUser.getUserId());
        outputOder.setPmId(pmId);
        System.out.println(outputOder);
        int i = outputOrderService.addOutputOrder(outputOder);
        return Result.success(i,"操作成功",200);
    }

    @RequestMapping("/list/sendOutputOrder")
    @ResponseBody
    public Object sendOutputOrder(@RequestBody Integer outId){
        int i = outputOrderService.sendOutputOrderByoutId(outId);
        String msg;
        if (i==1)
            msg = "操作成功";
        else msg = "操作失败";
        return Result.success(i,msg,200);
    }
}
