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
//        List<Product> products = productService.selectAllProduct();

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
            System.out.println("商品数"+productList.size());
        }

        return "views_front/product";
    }

    /**
     *跳转商品列表页（有分类侧边栏），并传入商品数据
     * */
    @RequestMapping("/product-list.html")
    public String productList(Model model,HttpServletRequest request,Integer categoryId,
                              @RequestParam(defaultValue = "1") int pageNo,
                              @RequestParam(defaultValue = "5") int pageSize/*,
                              @RequestParam("cid") String cid*/){
        //若为正常访问list页面（未分类）
        if(request.getParameter("cid")==null){
            //分页
            PageInfo<Product> pageInfo;
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

                //将所有商品对象传入页面
                model.addAttribute("productList",productList);
//            System.out.println(products);
                System.out.println("商品数"+productList.size());

            }
        }
        //通过header分类跳转，携带cid（商品类别）
        else{
            /*//分页
            PageInfo<Product> pageInfo;

            //从header传入商品种类id
            headerCategoryId = Integer.parseInt(request.getParameter("cid"));
            model.addAttribute("cid",headerCategoryId);
            System.out.println(headerCategoryId);

            pageInfo = productService.selectByCategory(pageNo, pageSize,headerCategoryId);


            //取出商品列表并注入视图
            List<Product> productList = pageInfo.getList();*/

            categoryId = Integer.parseInt(request.getParameter("cid"));
            List<Product> productList = productService.selectByCategory(categoryId);

            //将对应分类的商品传入页面
            model.addAttribute("productList",productList);

            if (productList.size()==0) {
                System.out.println("当前数据库中无商品！");
            }else{

                //将所有商品对象传入页面
                model.addAttribute("productList",productList);

                System.out.println(productList);
                System.out.println("当前种类商品数"+productList.size());

            }
        }

        return "views_front/product-list";
    }
}
