package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapperExtend {
    String selectStatusByNum(@Param("remarks_num") Byte status_num);

    void takeDeliveryOfProduct(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

    List<Order> selectByUserId(@Param("userId") Integer userId);
}
