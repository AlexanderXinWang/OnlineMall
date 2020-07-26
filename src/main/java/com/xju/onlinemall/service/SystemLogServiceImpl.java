package com.xju.onlinemall.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<SystemLog> getSystemLogAndSearchInfo(int pageNo, int pageSize, SystemLog systemLog) {
        //分页查询
        PageHelper.startPage(pageNo,pageSize);
        SystemLogExample systemLogExample = new SystemLogExample();
        //设置为查询登录用户的商品
        SystemLogExample.Criteria criteria = systemLogExample.createCriteria();
        //查看日志是否为空，日志为空则直接跳过，说明不是搜索栏触发的
        //不为空则是搜索栏触发的
        if (systemLog!=null){
            //如果日志id不为空，添加日志id条件
            if (systemLog.getLogId()!=null){
                criteria.andLogIdEqualTo(systemLog.getLogId());
            }
            //如果用户id不为空，添加用户id条件，
            if (systemLog.getUserId()!=null){
                criteria.andUserIdEqualTo(systemLog.getUserId());
            }
            //如果时间不为空，添加商时间条件，进行模糊查询
            if (systemLog.getCreateTime()!=null){
                criteria.andCreateTimeEqualTo(systemLog.getCreateTime());
            }
        }
        List<SystemLog> list =systemLogMapper.selectByMyExample(systemLogExample);//按时间降序排序
        //System.out.println(list);
        //得到分页器
        PageInfo<SystemLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
