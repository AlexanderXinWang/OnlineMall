package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Category;
import com.xju.onlinemall.common.domain.CategoryExample;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.ProductExample;
import com.xju.onlinemall.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        return categoryMapper.selectList();
    }

    /**
     *
     * 得到商品分类的分页器
     * */
    @Override
    public PageInfo<Category> getAllCategorys(int pageNo, int pageSize) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        CategoryExample categoryExample = new CategoryExample();
        List<Category> list = categoryMapper.selectByExample(categoryExample);
        //得到分页器
        PageInfo<Category> PageInfo = new PageInfo<>(list);

        return PageInfo;
    }
    /**
     *
     * 删除分类
     *
     * */

    @Override
    public int removeCategorysByCategoryIds(Integer... categoryIds) {
        int count=0;

        if (categoryIds!=null && categoryIds.length>0){
            for(Integer id:categoryIds){
                int i = categoryMapper.deleteByPrimaryKey(id);
                count=count+i;
            }
        }
        return count;

    }
}
