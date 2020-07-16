package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;


@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @RequestMapping("/addComment")
    @ResponseBody
    public Object addComent(HttpSession session,Integer productId,String author,String email,Integer rating,String comment){
        User user = (User)session.getAttribute("user");

        //获得用户主键
        Integer userId = user.getUserId();
        HashMap<String, Object> map = new HashMap<>();
        System.out.println("前台传来的值:"+author+" "+email+" "+rating+" "+comment+" "+productId);
        /**
         * 如果商品
         * */
        if (productId==null || email==null){
            map.put("success",false);
        }
        else {
            boolean b = commentService.insertIntoCommentByDetails(userId, productId, rating, comment, new Date());
            if (b){
                map.put("success",true);
            }
            else {
                map.put("success",false);
            }
        }
        return  map;
    }
}
