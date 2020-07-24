package com.xju.onlinemall.mapper.extend;

import com.xju.onlinemall.common.domain.OutputOder;

import java.util.List;

public interface OutputOderMapperExtend {
    List<OutputOder> selectOutputOrdersByUserId(Integer userId);
}
