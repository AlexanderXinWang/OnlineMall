package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Comment;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CommentService;
import com.xju.onlinemall.service.ProductService;
import com.xju.onlinemall.service.StarService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.PushBuilder;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    CommentService commentService;

    @Autowired
    StarService starService;

    /**
     *跳转商品页,并从数据库获取商品信息
     * */
    @RequestMapping("/product.html")
    public String commodity(HttpSession session, Model model,
                            @RequestParam(required = false) String s,
                            @RequestParam(defaultValue = "5") int product_cat,
                            @RequestParam(defaultValue = "0") int condition,
                            @RequestParam(defaultValue = "1") int pageNo,
                            @RequestParam(defaultValue = "12") int pageSize){
        User user = (User)session.getAttribute("user");
        Integer userId = user.getUserId();
        //分页
        PageInfo<Product> pageInfo=null;
        //未进行搜索
        if (s==null || s.equals("null")) {
            /**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             */
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.getAllProducts(pageNo, pageSize, userId);
                    break;
                case 1:
                    pageInfo = productService.getAllProductsByRate(pageNo,pageSize, userId);
                    break;
                case 2:
                    pageInfo = productService.getAllProductsByTime(pageNo,pageSize, userId);
                    break;
                case 3:
                    pageInfo = productService.getAllProductsByPriceASC(pageNo,pageSize, userId);
                    break;
                case 4:
                    pageInfo = productService.getAllProductsByPriceDESC(pageNo,pageSize, userId);
                    break;
            }
        }
        //搜索框搜索
        else {
            /**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             */
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.searchProductsByCategory(pageNo, pageSize,product_cat,s);
                    break;
                case 1:
                    pageInfo = productService.searchProductsByCategoryAndRate(pageNo, pageSize,product_cat,s);
                    break;
                case 2:
                    pageInfo = productService.searchProductsByCategoryAndTime(pageNo, pageSize,product_cat,s);
                    break;
                case 3:
                    pageInfo = productService.searchProductsByCategoryAndPriceASC(pageNo, pageSize,product_cat,s);
                    break;
                case 4:
                    pageInfo = productService.searchProductsByCategoryAndPriceDESC(pageNo, pageSize,product_cat,s);
                    break;
            }
        }



        if (pageInfo.getList()==null) {
            System.out.println("当前数据库中无商品！");
            return "404";
        }
        else{
            //取出商品列表并注入视图
            List<Product> productList = pageInfo.getList();
            //分页注入视图
            model.addAttribute("pageInfo", pageInfo);
            //将所有商品列表传入页面
            model.addAttribute("productList",productList);
            //设置页面筛选方式
            model.addAttribute("condition",condition);
            //搜索条件与商品种类置入页面
            model.addAttribute("cid",product_cat);
            model.addAttribute("s",s);
            System.out.println("此页商品数"+productList.size());
        }

        return "views_front/product";
    }

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    @RequestMapping("/product-list.html")
    public String productList(Model model,HttpServletRequest request,HttpSession session,
                              @RequestParam(required = false) String s,
                              @RequestParam(defaultValue = "0") double min,
                              @RequestParam(defaultValue = "9999") double max,
                              @RequestParam(defaultValue = "5") int cid,
                              @RequestParam(defaultValue = "0") int condition,
                              @RequestParam(defaultValue = "1") int pageNo,
                              @RequestParam(defaultValue = "5") int pageSize){
        User user = (User)session.getAttribute("user");
        Integer userId = user.getUserId();
        //分页
        PageInfo<Product> pageInfo;

        //若未进行搜索
        if(s==null || s.equals("null")){
            /**
             * 通过header分类跳转或页面筛选，携带cid（商品类别）
             * 1-数码
             * 2-家具电器
             * 3-食品
             * 4-服饰
             * 5-全部分类
             */
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.getProductsByCategoryAndPriceRange(pageNo,pageSize,cid,min,max,userId);
                    break;
                case 1:
                    pageInfo = productService.getProductsByCategoryAndPriceRangeAndRate(pageNo,pageSize,cid,min,max,userId);
                    break;
                case 2:
                    pageInfo = productService.getProductsByCategoryAndPriceRangeAndTime(pageNo,pageSize,cid,min,max,userId);
                    break;
                case 3:
                    pageInfo = productService.getProductsByCategoryAndPriceRangeAndPriceASC(pageNo,pageSize,cid,min,max,userId);
                    break;
                case 4:
                    pageInfo = productService.getProductsByCategoryAndPriceRangeAndPriceDESC(pageNo,pageSize,cid,min,max,userId);
                    break;
            }
        }
        //进行搜索
        else{
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.searchProductsByCategoryAndPriceRange(pageNo,pageSize,cid,min,max,s);
                    break;
                case 1:
                    pageInfo = productService.searchProductsByCategoryAndPriceRangeAndRate(pageNo,pageSize,cid,min,max,s);
                    break;
                case 2:
                    pageInfo = productService.searchProductsByCategoryAndPriceRangeAndTime(pageNo,pageSize,cid,min,max,s);
                    break;
                case 3:
                    pageInfo = productService.searchProductsByCategoryAndPriceRangeAndPriceASC(pageNo,pageSize,cid,min,max,s);
                    break;
                case 4:
                    pageInfo = productService.searchProductsByCategoryAndPriceRangeAndPriceDESC(pageNo,pageSize,cid,min,max,s);
                    break;
            }
        }

        if (pageInfo.getList()==null) {
            System.out.println("当前数据库中无商品！");
            return "404";
        }else{
            //取出商品列表并注入视图
            List<Product> productList = pageInfo.getList();
            //分页注入视图
            model.addAttribute("pageInfo", pageInfo);
            //将所有商品列表传入页面
            model.addAttribute("productList",productList);
            //设置页面筛选方式
            model.addAttribute("condition",condition);
            model.addAttribute("cid",cid);
            //将价格区间上下限注入页面
            model.addAttribute("min",min);
            model.addAttribute("max",max);
            //搜索内容注入页面
            model.addAttribute("s",s);

            System.out.println(pageInfo);
            System.out.println("此页商品数"+productList.size());
        }
        return "views_front/product-list";
    }


    /**
     *     处理价格区间筛选请求
     */
    @RequestMapping("/priceRangeFilter")
    @ResponseBody
    public Object priceRangeFilter(String min, String max) {
        ModelMap modelMap = new ModelMap();
        /*if (min==null & max==null){
            modelMap.put("success",false);
            modelMap.put("msg","/product-list.html");
        }
        else {
            modelMap.put("min",min);
            modelMap.put("max",max);
            modelMap.put("success",true);
            //若min为空max不为空
            if (min==null) {
                modelMap.put("msg","/product-list.html?max="+max);
            }
            else {
                //若min不为空max为空
                if (max==null) {
                    modelMap.put("msg","/product-list.html?min="+min);
                }
                //若min不为空max不为空
                else {
                    modelMap.put("msg","/product-list.html?min="+min+"&max="+max);
                }
            }
        }*/
        Double minDouble = Double.parseDouble(min);
        Double maxDouble = Double.parseDouble(max);
        if (minDouble>=maxDouble || maxDouble<=minDouble) {
            modelMap.put("success",false);
            modelMap.put("msg","/product-list.html");
        }
        else {
            modelMap.put("min",min);
            modelMap.put("max",max);
            modelMap.put("success",true);
            modelMap.put("msg","/product-list.html?min="+min+"&max="+max);
        }

        return modelMap;
    }


    /**
     *合并至————>/product.html
     */
    /*@RequestMapping("/productFilter")
    public String productFilterByPageSize(Model model, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0") int condition,
                                        @RequestParam(defaultValue = "1") int pageNo,
                                        @RequestParam(defaultValue = "12") int pageSize) {
        //分页
        PageInfo<Product> pageInfo=null;
        //若只传条件筛选（即没有设置每页显示多少件商品）
        if (request.getParameter("pageSize")==null) {
            //根据req的值判断按照什么条件筛选
            *//**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             *//*
//            condition = Integer.parseInt(request.getParameter("condition"));
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.getAllProducts(pageNo, pageSize);
                    break;
                case 1:
                    //TO-DO
                    pageInfo = productService.getAllProductsByRate(pageNo,pageSize);
                    System.out.println("TO-DO");
                    break;
                case 2:
                    pageInfo = productService.getAllProductsByTime(pageNo,pageSize);
                    break;
                case 3:
                    pageInfo = productService.getAllProductsByPriceASC(pageNo,pageSize);
                    break;
                case 4:
                    pageInfo = productService.getAllProductsByPriceDESC(pageNo,pageSize);
                    break;
            }
            model.addAttribute("condition",condition);
        }
        //只有pageSize值在product的controller中处理
        //既有condition值又有pageSize值时
        else {

            System.out.println("还没做");
        }


        if (pageInfo.getList()==null) {
            System.out.println("当前数据库中无商品！");
            return "404";
        }else{
            //取出商品列表并注入视图
            List<Product> productList = pageInfo.getList();
            //分页注入视图
            model.addAttribute("pageInfo", pageInfo);
            //将所有商品列表传入页面
            model.addAttribute("productList",productList);
            System.out.println("此页商品数"+productList.size());
        }
        return "views_front/product";
    }*/

    /**
     *合并至————>/product-list.html
     */
    /*@RequestMapping("/product-listFilter")
    public String productListFilterByPageSize(Model model, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0") int condition,
                                        @RequestParam(defaultValue = "1") int pageNo,
                                        @RequestParam(defaultValue = "5") int pageSize) {
        //分页
        PageInfo<Product> pageInfo=null;
        //若只传条件筛选（即没有设置每页显示多少件商品）
        if (request.getParameter("pageSize")==null) {
            //根据req的值判断按照什么条件筛选
            *//**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             *//*
//            condition = Integer.parseInt(request.getParameter("condition"));
            switch (condition) {
                default:
                    //获取分页信息与商品列表
                    pageInfo = productService.getAllProducts(pageNo, pageSize);
                    break;
                case 1:
                    //TO-DO
                    pageInfo = productService.getAllProductsByRate(pageNo,pageSize);
                    System.out.println("TO-DO");
                case 2:
                    pageInfo = productService.getAllProductsByTime(pageNo,pageSize);
                    break;
                case 3:
                    pageInfo = productService.getAllProductsByPriceASC(pageNo,pageSize);
                    break;
                case 4:
                    pageInfo = productService.getAllProductsByPriceDESC(pageNo,pageSize);
                    break;
            }
            model.addAttribute("condition",condition);
        }
        //只有pageSize值在product的controller中处理
        //既有condition值又有pageSize值时
        else {

            System.out.println("还没做");
        }

        if (pageInfo.getList()==null) {
            System.out.println("当前数据库中无商品！");
            return "404";
        }else{
            //取出商品列表并注入视图
            List<Product> productList = pageInfo.getList();
            //分页注入视图
            model.addAttribute("pageInfo", pageInfo);
            //将所有商品列表传入页面
            model.addAttribute("productList",productList);
            System.out.println("此页商品数"+productList.size());
        }
        return "views_front/product-list";
    }*/
}
