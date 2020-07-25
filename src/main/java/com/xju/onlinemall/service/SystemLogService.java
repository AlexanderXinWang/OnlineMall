package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;

public interface SystemLogService {
    PageInfo<SystemLog> getSystemLogByUserIdAndSearchInfo(int pageNo, int pageSize, Integer userId);
}
