package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.StarExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StarMapperExtend {
    void deleteStarsByIds(@Param("ids") List<Integer> ids);
    List<Star> selectByMultiExample(Integer userId);
    Product selectProductIdAndStarIdByPrimaryKey(Integer productId, Integer userId);
}
