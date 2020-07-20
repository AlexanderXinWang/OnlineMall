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

        //取出商品列表并注入视图
        List<Product> productList = pageInfo.getList();

        //分页注入视图
        model.addAttribute("pageInfo",pageInfo);

        //打印检测
        System.out.println(pageInfo);
        System.out.println(productList);

        if (productList.size()==0) {
            System.out.println("当前数据库中无商品！");
        }else{
            //将所有商品列表传入页面
            model.addAttribute("productList",productList);
            System.out.println("此页商品数"+productList.size());
        }

        return "views_front/product";
    }

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    @RequestMapping("/product-list.html")
    public String productList(Model model,HttpServletRequest request,Integer categoryId,ModelMap modelMap,
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
            categoryId = Integer.parseInt(request.getParameter("cid"));
            model.addAttribute("cid",categoryId);
            pageInfo = productService.getByCategory(pageNo, pageSize,categoryId);
        }

        //分页注入视图
        model.addAttribute("pageInfo",pageInfo);
        //取出商品列表并注入视图
        List<Product> productList = pageInfo.getList();

        if (productList.size()==0) {
            System.out.println("当前数据库中无商品！");
            return "404";
        }else{
            //将所有商品对象传入页面
            model.addAttribute("productList",productList);
            //分页注入视图
            model.addAttribute("pageInfo",pageInfo);
            System.out.println("当前页商品数"+productList.size());
        }
        return "views_front/product-list";
    }

    /*@RequestMapping("/productListByPageSize")
    @ResponseBody
    public Object productListByPageSize(Model model,@Param("pageSize") String pageSize) {
        model.addAttribute("success", true);
        model.addAttribute("msg", "/product.html?pageSize="+pageSize);
        System.out.println(pageSize);
        return model;
    }*/
}
