package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.Product;

import java.util.List;

public interface CategoryService {
    List<Category> list();

    /**
     *
     *
     * */
    PageInfo<Category> getAllCategorys(int pageNo, int pageSize);

    int removeCategorysByCategoryIds(Integer... categoryIds);

}
