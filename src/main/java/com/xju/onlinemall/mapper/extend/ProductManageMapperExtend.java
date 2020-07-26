package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.ProductManage;

public interface ProductManageMapperExtend {
    ProductManage selectPmIdByUserId(Integer userId);
}
