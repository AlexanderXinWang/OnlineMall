package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/listByCategory")
    @ResponseBody
    public List<Product> listByCategory(){  //通过类别查询商品，以“数码”为例
        String categoryName = null;
        //int category_id = 0;
        categoryName="数码";

        return productService.selectByCategory(categoryName);
    }

    /**
    * TO-DO
    * */
    @GetMapping("/showAllProduct")
    public List<Product> showAllProduct() {
        return productService.selectAllProduct();
    }
}
