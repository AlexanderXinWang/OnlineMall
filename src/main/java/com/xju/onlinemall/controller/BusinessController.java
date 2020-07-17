package com.xju.onlinemall.controller;


import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BusinessController {
    @Autowired
    private ProductService productService;


    /**
     *
     * 获得商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/products")
    @ResponseBody
    public Object productList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize,Integer pmId){

        PageInfo<Product> pageInfo=null;
        pageInfo = productService.getAllProducts(pageNo, pageSize);
        return Result.success(pageInfo);
    }

    /**
     *
     * 删除商品
     *
     * */
    @RequestMapping("/list/deleteProducts")
    @ResponseBody
    public Object deleteProudcts(@RequestBody Integer[] productIds){
        int i = productService.removeProudctsByProductIds(productIds);

        return Result.success(i,"操作成功",200);
    }
}
