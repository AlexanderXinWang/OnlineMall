package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.SystemLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemLogMapperExtend {

    String selectUserNameByLogId(@Param("logId") Integer logId);

    List<SystemLog> selectByMyExample(@Param("logId") Integer logId, @Param("userId") Integer userId, @Param("time") String time);
}
