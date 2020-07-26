package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.SystemLogExample;

import java.util.List;

public interface SystemLogMapperExtend {
    List<SystemLog> selectByMyExample(SystemLogExample systemLogExample);
}
