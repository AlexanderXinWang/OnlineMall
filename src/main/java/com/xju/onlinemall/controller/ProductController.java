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

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    CommentService commentService;

    @RequestMapping("/product/listByCategory")
    @ResponseBody
    public List<Product> listByCategory(){  //通过类别查询商品，以“数码”为例
        String categoryName = null;
        //int category_id = 0;
        categoryName="数码";

        return productService.selectByCategory(categoryName);
    }

    /**
     *跳转商品页,并从数据库获取商品信息
     * */
    @GetMapping("/product.html")
    public String commodity(HttpSession session, Model model,
                            @RequestParam(defaultValue = "1") int pageNo,
                            @RequestParam(defaultValue = "10") int pageSize){
        List<Product> products = productService.selectAllProduct();

        if (products.size()==0) {
            System.out.println("当前数据库中无商品！");
        }else{
            //将所有商品对象传入页面
            model.addAttribute("productList",products);
//            System.out.println(products);
            System.out.println(products.size());
        }

        //分页
        PageInfo<Product> pageInfo=null;
        pageInfo = productService.getAllProducts(pageNo, pageSize);
        System.out.println(pageInfo);
        model.addAttribute("pageInfo",pageInfo);

        return "views_front/product";
    }

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    @GetMapping("/product-list.html")
    public String productList(Model model,@Param("categoryName")String categoryName){
        System.out.println(categoryName);
        //categoryName用来做按类名条件查询
        List<Product> products = productService.selectAllProduct();

        if (products.size()==0) {
            System.out.println("当前数据库中无商品！");
        }else{
            //将所有商品对象传入页面
            model.addAttribute("productList",products);
//            System.out.println(products);
            System.out.println("商品数"+products.size());
        }

        return "views_front/product-list";
    }
}
