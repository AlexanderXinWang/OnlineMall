package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.SystemLogExample;
import com.xju.onlinemall.mapper.SystemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogServiceImpl implements SystemLogService{
    @Autowired
    SystemLogMapper systemLogMapper;

    @Override
    public PageInfo<SystemLog> getSystemLogByUserIdAndSearchInfo(int pageNo, int pageSize, Integer userId) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        SystemLogExample systemLogExample=new SystemLogExample();
        //设置为查询登录用户的日志
        SystemLogExample.Criteria criteria = systemLogExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<SystemLog> list =systemLogMapper.selectByExample(systemLogExample);
        System.out.println(list);
        //得到分页器
        PageInfo<SystemLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
