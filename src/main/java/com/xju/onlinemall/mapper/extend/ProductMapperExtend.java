package com.xju.onlinemall.mapper.extend;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.extend.pCountCName;
import org.apache.ibatis.annotations.Param;

import javax.rmi.PortableRemoteObject;
import java.util.List;

public interface ProductMapperExtend {
    List<Product> selectByCategory(@Param("categoryId") Integer categoryId);
    //根据商品id列表进行批量查询
    List<Product> selectByProductIdList(@Param("productIdLists") List<Integer> productIdLists);

    List<Product> selectAllProduct();

    List<Product> selectByPriceASC();

    List<Product> selectByPriceDESC();

    List<Product> selectByRate();

    List<Product> selectByTime();

    String selectSellerByProductId(@Param("productId") int productId);

    List<Product> selectByPriceRangeAndRate(@Param("min")double min, @Param("max")double max);

    List<Product> selectByCategoryAndPriceRangeAndRate(@Param("cid") int cid, @Param("min")double min, @Param("max")double max);

    List<Product> selectBySearchAndRate(String s);

    List<Product> selectBySearchAndCategoryAndRate(int cid, String s);

    List<Product> selectBySearchAndPriceRangeAndRate(String s, double min, double max);

    List<Product> selectBySearchAndCategoryAndPriceRangeAndRate(int cid, String s, double min, double max);

    List<pCountCName> selectNumGroupByCategory(Integer userId);
}
