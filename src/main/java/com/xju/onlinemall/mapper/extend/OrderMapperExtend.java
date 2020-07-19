package com.xju.onlinemall.mapper.extend;

import org.apache.ibatis.annotations.Param;

public interface OrderMapperExtend {
    String selectStatusByNum(@Param("remarks_num") Byte status_num);

    void takeDeliveryOfProduct(@Param("userId") Integer userId, @Param("orderId") Integer orderId);
}
