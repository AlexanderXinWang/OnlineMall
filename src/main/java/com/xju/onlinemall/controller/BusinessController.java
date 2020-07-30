package com.xju.onlinemall.controller;


import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.domain.extend.pCountCName;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.CategoryService;
import com.xju.onlinemall.service.ProductService;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class BusinessController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    UserService userService;

    /**
     *
     * 商品搜索功能
     * 搜索框  获得商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/searchProducts")
    @ResponseBody
    public Object productListBySearch(Product product,HttpSession session,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        User adminUser =(User) session.getAttribute("adminUser");

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("搜索和获取商品信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        PageInfo<Product> pageInfo = productService.getAllProductsBypmIdAndSearchInfo(pageNo, pageSize,adminUser.getUserId(),product);
        return Result.success(pageInfo);
    }



    /**
     *
     * 获得商品列表信息
     * 以JSON的数据格式传输到前端
     * 目前废弃，已经整合到上面的productListBySearch方法中
     * */
    @RequestMapping("/list/products")
    @ResponseBody
    public Object productList(HttpSession session,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize,Integer pmId){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<Product> pageInfo=productService.getAllProductsBypmId(pageNo, pageSize,adminUser.getUserId());
        return Result.success(pageInfo);
    }

    /**
     *
     * 删除商品
     *
     * */
    @RequestMapping("/list/deleteProducts")
    @ResponseBody
    public Object deleteProducts(@RequestBody Integer[] productIds,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");


        int i = productService.removeProductsByProductIds(productIds);

        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("删除商品操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        return Result.success(i,"操作成功",200);
    }

    /**
     *
     * 获得对应商户的商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/searchCategorys")
    @ResponseBody
    public Object searchCategorys(HttpSession session,Category category,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId){

        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("搜索和获取商品分类信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        PageInfo<Category> pageInfo = categoryService.getAllCategorysBySerchInfo(pageNo, pageSize,category);
        //返回结果
        return Result.success(pageInfo);
    }

    /**
     *
     * 获得对应商户的商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/list/categorys")
    @ResponseBody
    public Object categorytList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, Integer pmId){
//        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<Category> pageInfo= categoryService.getAllCategorys(pageNo, pageSize);
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
    public Object deleteCategorys(@RequestBody Integer[] categoryIds,HttpSession session){
        int i = categoryService.removeCategorysByCategoryIds(categoryIds);

        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("删除商品分类信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        return Result.success(i,"操作成功",200);
    }
    /**
     *
     * 添加分类
     *
     * */
    @RequestMapping("/list/addCategory")
    @ResponseBody
    public Object addCategory(@RequestBody Category category,HttpSession session){
        //查看后台获取到的数据
//        System.out.println(category);
        int i=categoryService.addCategory(category);

        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("添加商品分类信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        return Result.success(i,"操作成功",200);
    }
    /**
     * 查询分类对象，回显页面
     *
     * */
    @GetMapping("/list/updateCategoryIdToBackPage")
    public String updateCategoryIdToBackPage(ModelMap modelMap,Integer categoryId){
//        System.out.println(categoryId);
        //根据Id查询该商品
        Category category= categoryService.selectByCategoryId(categoryId);
        //把商品放入其中进行显示
//        System.out.println(category);
        modelMap.put("oldCategory",category);

        return "views/list-backCategoryShowAndUpdate";
    }
    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/showCategoryDetail")
    public String showCategoryDetail(ModelMap modelMap,Integer categoryId,HttpSession session){
//        System.out.println(categoryId);
        //根据Id查询该商品
        Category category= categoryService.selectByCategoryId(categoryId);
        //把商品放入其中进行显示
        modelMap.put("oldCategory",category);


        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("查看分类详细信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        return "views/list-backCategoryShowDetail";
    }


    /**
     *
     * 修改商品
     * /list/updateCategory
     * */
    @RequestMapping("/list/updateCategory")
    @ResponseBody
    public Object updateCategory(@RequestBody Category category,HttpSession session){

//        System.out.println("分类修改:"+category);
        int i = categoryService.updateCategory(category);

        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("更新商品分类信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        return Result.success(i,"操作成功",200);
    }


    /**
     *
     * 添加商品
     *
     * */
    @RequestMapping("/list/addProduct")
    @ResponseBody
    public Object addProduct(@RequestBody Product product,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("添加商品操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);


        //设置添加的时间
        Date date = new Date();
        product.setAddTime(date);
        //查看后台获取到的数据
//        System.out.println(product);
        int i = productService.addProduct(product);



        return Result.success(i,"操作成功",200);
    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/updateProductToBackPage")
    public String getProduct(ModelMap modelMap,Integer productId){


//        System.out.println(productId);
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
    public String showProductDetail(ModelMap modelMap,Integer productId,HttpSession session){

        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("查看商品详细信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);
//        System.out.println(productId);
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
    public Object updateProduct(@RequestBody Product product,HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        SystemLog systemLog = new SystemLog();
        systemLog.setUserId(adminUser.getUserId());
        systemLog.setOperation("更新商品信息操作");
        systemLog.setLevel(2);
        systemLog.setCreateTime(new Date());
        userService.insertLogByUser(systemLog);

        //设置添加的时间
        Date date = new Date();
        product.setAddTime(date);
        //查看后台获取到的数据
//        System.out.println(product);
        int i = productService.updateProduct(product);
        return Result.success(i,"操作成功",200);
    }

    @RequestMapping("/list/productPartCount")
    @ResponseBody
    public  Object productPartCount(HttpSession session){
        User adminUser =(User) session.getAttribute("adminUser");
        //获得对应用户的全部商品
        List<pCountCName> pCountCNameList = productService.selectAllProductGroupByCategorty(adminUser.getUserId());

//        HashMap<Object, Object> result = new HashMap<>();
//        产生键值对
//        List<Map<String,Object>> list=new ArrayList<>();

//        for (pCountCName p:pCountCNameList){
//            Map<String, Object> temp = new HashMap<>();
//            temp.put("value",p.getCount());
//            temp.put("name",p.getCategoryName());
//            list.add(temp);
//        }
//        result.put("pCountCNameList",pCountCNameList);
//        result.put("list",list);

        return pCountCNameList;
    }

}
