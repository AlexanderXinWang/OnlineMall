package com.xju.onlinemall.controller;


import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.CategoryService;
import com.xju.onlinemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class BusinessController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


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

    /**
     *
     * 获得商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/categorys")
    @ResponseBody
    public Object categorytList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize,Integer pmId){

        PageInfo<Category> pageInfo=null;
        pageInfo = categoryService.getAllCategorys(pageNo, pageSize);
        //返回结果
        return Result.success(pageInfo);
    }

    /**
     *
     * 删除分类
     *
     * */
    @RequestMapping("/list/deleteCategorys")
    @ResponseBody
    public Object deleteCategorys(@RequestBody Integer[] categoryIds){
        int i = categoryService.removeCategorysByCategoryIds(categoryIds);

        return Result.success(i,"操作成功",200);
    }

    /**
     *
     * 添加商品
     *
     * */
    @RequestMapping("/list/addProduct")
    @ResponseBody
    public Object addProduct(@RequestBody Product product){
        //设置添加的时间
        Date date = new Date();
        product.setAddTime(date);
        //查看后台获取到的数据
//        System.out.println(product);
        int i = productService.addProduct(product);
        return Result.success(1,"操作成功",200);
    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/updateProductToBackPage")
    public String getProduct(ModelMap modelMap,Integer productId){
        System.out.println(productId);
        //根据Id查询该商品
        Product product = productService.selectByProductId(productId);
        //把商品放入其中进行显示
        modelMap.put("oldProduct",product);

        return "views/list-backProductShowAndUpdate";
    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/showProductDetail")
    public String showProductDetail(ModelMap modelMap,Integer productId){
        System.out.println(productId);
        //根据Id查询该商品
        Product product = productService.selectByProductId(productId);
        //把商品放入其中进行显示
        modelMap.put("oldProduct",product);

        return "views/list-backProductShowDetail";
    }

    /**
     * 更新商品信息
     *
     * */
    @RequestMapping("/list/updateProduct")
    @ResponseBody
    public Object updateProduct(@RequestBody Product product){
        //设置添加的时间
        Date date = new Date();
        product.setAddTime(date);
        //查看后台获取到的数据
//        System.out.println(product);
        int i = productService.updateProduct(product);
        return Result.success(1,"操作成功",200);
    }


}
