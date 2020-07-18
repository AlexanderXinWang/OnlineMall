package com.xju.onlinemall.mapper.extend;

import org.apache.ibatis.annotations.Param;

public interface OrderMapperExtend {
    String selectStatusByNum(@Param("remarks_num") Byte status_num);
}
