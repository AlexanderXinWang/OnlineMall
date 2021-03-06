package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.*;
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
    /**
    *
     * 添加分类
     *
    * */
    @Override
    public int addCategory(Category category) {
        int insert = categoryMapper.insert(category);
        return insert;
    }
    /**
     *
     * 查询分类
     *
     * */
    @Override
    public Category selectByCategoryId(Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        return category;
    }
    /**
     *
     * 修改分类
     *
     * */
    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public PageInfo<Category> getAllCategorysBySerchInfo(int pageNo, int pageSize, Category category) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (category.getCategoryId()!=null){
            //如果用户填了分类id，则添加该条件
            criteria.andCategoryIdEqualTo(category.getCategoryId());
        }
        if (category.getCategoryName()!=null){
            //如果用户填了分类id，则添加该条件, 进行模糊查询
            criteria.andCategoryNameLike("%"+category.getCategoryName()+"%");
        }

        List<Category> list = categoryMapper.selectByExample(categoryExample);

        //得到分页器
        PageInfo<Category> PageInfo = new PageInfo<>(list);

        return PageInfo;
    }
}
