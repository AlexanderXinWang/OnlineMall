package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.Star;
import org.springframework.stereotype.Service;

import java.util.List;
public interface StarService {
    public PageInfo<Product> findStars(int pageNo, int pageSize, Integer userId);
    public void deleteStarById(Integer starId);
    void deleteStarsByIds(List<Integer> ids);
}
