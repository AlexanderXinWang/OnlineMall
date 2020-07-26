package com.xju.onlinemall.controller;

import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.SystemLog;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class SystemLogController {
    @Autowired
    SystemLogService systemLogService;


    @RequestMapping("/systemLogController/systemLogSearch")
    @ResponseBody
    public Object productListBySearch(HttpSession session, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<SystemLog> pageInfo=null;

        //把方法改一下吧，系统日志获取的是所有的日志信息，不是管理员一个人的日志信息

        pageInfo = systemLogService.getSystemLogByUserIdAndSearchInfo(pageNo, pageSize,adminUser.getUserId());
        return Result.success(pageInfo);
    }
}
