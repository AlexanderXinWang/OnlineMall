package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Comment;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.service.CommentService;
import com.xju.onlinemall.service.ProductService;
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
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    CommentService commentService;

    /**
     *跳转商品页,并从数据库获取商品信息
     * */
    @RequestMapping("/product.html")
    public String commodity(HttpSession session, Model model,
                            @RequestParam(defaultValue = "1") int pageNo,
                            @RequestParam(defaultValue = "12") int pageSize){
        //分页
        PageInfo<Product> pageInfo=null;
        //获取分页信息与商品列表
        pageInfo = productService.getAllProducts(pageNo, pageSize);

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
            //设置页面默认筛选方式
            model.addAttribute("condition",0);
            System.out.println("此页商品数"+productList.size());
        }

        return "views_front/product";
    }

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    @RequestMapping("/product-list.html")
    public String productList(Model model,HttpServletRequest request,ModelMap modelMap,
                              @RequestParam(defaultValue = "1") int pageNo,
                              @RequestParam(defaultValue = "5") int pageSize){
        //分页
        PageInfo<Product> pageInfo;

        //若为正常访问list页面（未分类）
        if(request.getParameter("cid")==null){
            /*if(min!=null&&max!=null){
                Double minPrice = Double.parseDouble(min);
                Double maxPrice = Double.parseDouble(max);
                //获取筛选后的分页信息与商品列表
                pageInfo = productService.selectByPrice(pageNo,pageSize,minPrice,maxPrice);
                modelMap.addAttribute("min",minPrice);
                modelMap.addAttribute("max",maxPrice);
                modelMap.put("success", true);
            }else{*/

            //获取分页信息与商品列表
            pageInfo = productService.getAllProducts(pageNo, pageSize);
        }

        //通过header分类跳转，携带cid（商品类别）
        else{
            Integer categoryId = Integer.parseInt(request.getParameter("cid"));
            model.addAttribute("cid",categoryId);
            pageInfo = productService.getByCategory(pageNo, pageSize,categoryId);
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
            //设置页面默认筛选方式
            model.addAttribute("condition",0);
            System.out.println("此页商品数"+productList.size());
        }
        return "views_front/product-list";
    }

    /**
     *
     */
    @RequestMapping("/productFilter")
    public String productFilterByPageSize(Model model, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0") int condition,
                                        @RequestParam(defaultValue = "1") int pageNo,
                                        @RequestParam(defaultValue = "12") int pageSize) {
        //分页
        PageInfo<Product> pageInfo=null;
        //若只传条件筛选（即没有设置每页显示多少件商品）
        if (request.getParameter("pageSize")==null) {
            //根据req的值判断按照什么条件筛选
            /**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             */
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
    }

    @RequestMapping("/product-listFilter")
    public String productListFilterByPageSize(Model model, HttpServletRequest request,
                                        @RequestParam(defaultValue = "0") int condition,
                                        @RequestParam(defaultValue = "1") int pageNo,
                                        @RequestParam(defaultValue = "5") int pageSize) {
        //分页
        PageInfo<Product> pageInfo=null;
        //若只传条件筛选（即没有设置每页显示多少件商品）
        if (request.getParameter("pageSize")==null) {
            //根据req的值判断按照什么条件筛选
            /**
             * 0-默认
             * 1-按评分排序
             * 2-按上架时间排序
             * 3-从低到高价格排序
             * 4-从高到低价格排序
             */
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
    }
}
