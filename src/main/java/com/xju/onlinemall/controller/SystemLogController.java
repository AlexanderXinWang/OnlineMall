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

    /**
     *
     * 商品搜索功能
     * 搜索框  获得商品列表信息
     * 以JSON的数据格式传输到前端
     *
     * */
    @RequestMapping("/systemLogController/systemLogSearch")
    @ResponseBody
    public Object productListBySearch(HttpSession session, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        User adminUser =(User) session.getAttribute("adminUser");
        PageInfo<SystemLog> pageInfo=null;
        pageInfo = systemLogService.getSystemLogByUserIdAndSearchInfo(pageNo, pageSize,adminUser.getUserId());
        return Result.success(pageInfo);
    }
}
