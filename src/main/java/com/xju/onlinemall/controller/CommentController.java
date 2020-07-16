package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Comment;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CommentService;
import com.xju.onlinemall.service.ProductService;
import com.xju.onlinemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Controller
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    /**
     *
     * 添加评论的接口
     *
     * 请注意前端页面字段是否和下面函数的字段名称一致！！
     *
     * */
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
    /**
     * 跳转到商品详细页面以及商品评论
     * 请注意，默认传入商品的id是1
     * 访问该商品页面时,请传入商品id,这是对评价展示功能而已实现的
     * 如果你要实现展示商品的功能,请添加获得商品信息的相关逻辑代码实现
     * 并添加到modelMap中去
     * */
    @RequestMapping("/single-product-simple.html")
    public String singleProduct(ModelMap modelMap,Integer productId){

        if (productId==null){
            productId=1;
        }
        /**
         * 获得用户点击的商品信息
         * */
        Product productSingle =  productService.selectByProductId(productId);

        /**
         *
         * 获得该商品评论的列表
         * */
        List<Comment> comments = commentService.selectByProductId(productId);

        /**
         *
         * 检测该商品评论是否获得成功
         * */
        if (productSingle == null){
            System.out.println("后台提示：商品信息获取失败！默认设置获取id为1的商品,请注意！！！！");
            Product product = productService.selectByProductId(1);
            productSingle=product;
        }
        /**
         *
         * 如果该商品的评价是0
         *  给他设置默认的评价
         * */
        if (comments==null || comments.size()==0){
            System.out.println("后台提示:商品评论获得失败！设置默认的评论展示！！");
            Comment comment = new Comment();
            comment.setProductId(1);
            comment.setUserId(1);
            comment.setScore(10);
            comment.setCommentTime(new Date());
            comment.setContext("该商品没有评价！系统默认展示该评价");

            comment.setUsername("系统默认");
            comment.setCount(1);

            comments.add(comment);
            modelMap.put("commentsList",comments);
            //添加商品信息到modelmap中
            modelMap.put("productSingle",productSingle);
            modelMap.put("commentCount",1);
        }
        else {
            //传入评论数
            modelMap.put("commentCount",comments.size());
            //添加商品信息到modelmap中
            modelMap.put("productSingle",productSingle);

            modelMap.put("commentsList",comments);
        }
        System.out.println("后台提示：查看是否获得和注入正确的商品属性："+productSingle);
        return "views_front/single-product-simple";
    }
}
