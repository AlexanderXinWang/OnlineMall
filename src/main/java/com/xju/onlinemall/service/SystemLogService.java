package com.xju.onlinemall.service;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.SystemLog;

public interface SystemLogService {
    PageInfo<SystemLog> getSystemLogAndSearchInfo(int pageNo, int pageSize, SystemLog systemLog ,String logTime);

    int deleteSystemLogsBylogIds(Integer... logIds);

    SystemLog selectByLogId(Integer logId);

    String selectUserNameByLogId(Integer logId);
}
