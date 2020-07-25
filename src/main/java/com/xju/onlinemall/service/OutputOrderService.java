package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.OutputOder;

public interface OutputOrderService {
    PageInfo<OutputOder> getAllOutputOrders(int pageNo, int pageSize, Integer userId);

    OutputOder selectOneOutputOrderByOutId(Integer outId);
}
