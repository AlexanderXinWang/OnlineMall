package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.OutputOder;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.OutputOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OutputOrderController {
    @Autowired
    private OutputOrderService outputOrderService;

//    @RequestMapping("/list")
//    @ResponseBody
//    public List<Category> list(ModelMap modelMap){  //查询商品类

//        List<Category> list=categoryService.list();
//        modelMap.addAttribute("categoryList",list);
////        System.out.println(list);
//        return list;
//    }
    @RequestMapping("/list/outputOrders")
    @ResponseBody
    public Object categorytList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId, HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<OutputOder> pageInfo=null;
        pageInfo = outputOrderService.getAllOutputOrders(pageNo, pageSize, adminUser.getUserId());
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

}
