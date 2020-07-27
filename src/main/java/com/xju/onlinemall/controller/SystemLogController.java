package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class SystemLogController {
    @Autowired
    SystemLogService systemLogService;


    @RequestMapping("/list/systemLogSearch")
    @ResponseBody
    public Object productListBySearch(SystemLog systemLog, String logTime,@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        PageInfo<SystemLog> pageInfo=null;

        //把方法改一下吧，系统日志获取的是所有的日志信息，不是管理员一个人的日志信息

        pageInfo = systemLogService.getSystemLogAndSearchInfo(pageNo, pageSize,systemLog,logTime);
        return Result.success(pageInfo);
    }

    /**
     * 查询对象，回显页面
     *
     * */
    @GetMapping("/list/showSystemLogDetail")
    public String showProductDetail(ModelMap modelMap, Integer logId){
        System.out.println(logId);
        //根据Id查询该商品
        SystemLog systemLog = systemLogService.selectByLogId(logId);
        String userName = systemLogService.selectUserNameByLogId(logId);
        System.out.println(systemLog);
        //把商品放入其中进行显示
        modelMap.put("systemLog",systemLog);
        modelMap.put("userName",userName);

        return "views/list-backSystemLogDetail";
    }

    /**
     *
     * 删除日志
     *
     * */
    @RequestMapping("/list/deleteSystemLogs")
    @ResponseBody
    public Object deleteProducts(@RequestBody Integer[] logIds){
        //System.out.println(logIds[0]);
        //System.out.println(logIds[1]);
        int i = systemLogService.deleteSystemLogsBylogIds(logIds);

        return Result.success(i,"操作成功",200);
    }
}
