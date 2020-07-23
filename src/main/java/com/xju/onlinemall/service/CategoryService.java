package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface CategoryService {
    List<Category> list();


    PageInfo<Category> getAllCategorys(int pageNo, int pageSize);

    int removeCategorysByCategoryIds(Integer... categoryIds);

    int addCategory(Category category);

    Category selectByCategoryId(Integer categoryId);

    int updateCategory(Category category);
     /**
     *
     * 根据搜索信息获取分类列表
     *
     * */
    PageInfo<Category> getAllCategorysBySerchInfo(int pageNo, int pageSize, Category category);
}
