package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.OutputOder;

public interface OutputOrderService {
    PageInfo<OutputOder> getAllOutputOrders(int pageNo, int pageSize, Integer userId, boolean isRemoved);

    OutputOder selectOneOutputOrderByOutId(Integer outId);

    int removeOutputOrdersLogicallyByoutIds(Integer... outIds);

    int updateOutputOrder(OutputOder outputOder);

    int addOutputOrder(OutputOder outputOder);

    Integer getPmIdByUserId(Integer userId);

    int sendOutputOrderByoutId(Integer outId);
}
