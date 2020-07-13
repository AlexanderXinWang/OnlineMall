package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ResponseBody
    public List<Category> list(){  //查询商品类别及其下属商品
        return categoryService.list();
    }
}
