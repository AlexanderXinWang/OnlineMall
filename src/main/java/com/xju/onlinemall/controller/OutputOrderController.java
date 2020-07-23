package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.OutputOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OutputOrderController {
//    @Autowired
//    private OutputOrderService outputOrderService;

//    @RequestMapping("/list")
//    @ResponseBody
//    public List<Category> list(ModelMap modelMap){  //查询商品类

//        List<Category> list=categoryService.list();
//        modelMap.addAttribute("categoryList",list);
////        System.out.println(list);
//        return list;
//    }
//    @RequestMapping("/list/outputOrders")
//    @ResponseBody
//    public Object categorytList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId){
////        User adminUser =(User) session.getAttribute("adminUser");
//        PageInfo<Category> pageInfo=null;
//        pageInfo = categoryService.getAllCategorys(pageNo, pageSize);
//        //返回结果
//        return Result.success(pageInfo);
//    }

//    SELECT out_id,out_number,product_name,out_status,out_date FROM tb_output_order,tb_product WHERE tb_output_order.`pm_id` = (SELECT pm_id FROM tb_product_manage WHERE tb_product_manage.`user_id` = 2) AND tb_product.`product_id` = tb_output_order.`product_id`;


}
