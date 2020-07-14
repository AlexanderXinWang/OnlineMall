package com.xju.onlinemall;

import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.mapper.*;
import com.xju.onlinemall.service.CartService;
import com.xju.onlinemall.service.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class OnlinemallApplicationTests {
    @Autowired
    CategoryMapper mapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartService cartService;
    @Test
    public void t1(){
        List<Category> categories = mapper.selectByExample(new CategoryExample());
        System.out.println(categories);
    }
    //测试获取用户
    @Test
    public void t2(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo("user1").andPasswordEqualTo("123");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users.size());
    }
    //测试插入日志信息
    @Test
    public void t3(){
        SystemLog systemLog = new SystemLog();
        //日志中要有id为1的用户
        systemLog.setUserId(1);
        systemLog.setOperation("登录操作");
        systemLog.setLevel(1);
        systemLog.setCreateTime(new Date());
        systemLogMapper.insert(systemLog);
    }
    //测试商品插入功能,全部属性正常时
    @Test
    public void t4(){
        Comment comment = new Comment();
        comment.setUserId(1);
        comment.setProductId(1);
        comment.setScore(10);
        comment.setContext("商品评价是指生产厂家、商家或者消费者根据具体商品的性能、规格、材质、使用寿命、外观等商品的内在价值设定一个可量化或定性的评价体系，由消费者对商品使用价值进行评价的过");
        comment.setCommentTime(new Date());
        boolean b = commentService.insertIntoCommentByComment(comment);
        System.out.println(b);
    }
    //测试商品插入功能,comment为空或用户id或产品id为空时，测试结果执行成功
    @Test
    public void t5(){
        Comment comment = new Comment();
        //不设置用户id
        comment.setUserId(1);
        //不设置商品id
//        comment.setProductId(1);
        comment.setScore(10);
        comment.setContext("商品评价是指生产厂家、商家或者消费者根据具体商品的性能、规格、材质、使用寿命、外观等商品的内在价值设定一个可量化或定性的评价体系，由消费者对商品使用价值进行评价的过");
        comment.setCommentTime(new Date());
        //设置comment为空时
        /**
         * 评论对象为空,插入失败
         * false
         * */
//        comment=null;
        boolean b = commentService.insertIntoCommentByComment(comment);
        System.out.println(b);
    }
    //测试商品插入功能,默认设置是否生效
    @Test
    public void t6(){
        boolean b = commentService.insertIntoCommentByDetails(1,1,null,"",null);
        System.out.println(b);
    }
    //测试查询
    @Test
    public void t7(){
//        List<Comment> comments = commentService.selectByUserId(null);
//        List<Comment> comments = commentService.selectByProductId(null);
//        List<Comment> comments = commentService.selectByUserIdAndProductId(null, 1);
        List<Comment> comments = commentService.selectByUserIdAndProductId(1, 1);
        System.out.println(comments);
    }
    //测试根据购物车id,查询商品
    @Test
    public void t8(){

        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(1);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart: carts) {
            productIds.add(cart.getProductId());
        }
        List<Product> list = cartService.getCartListByUserId(1);
//        List<Product> list= cartMapper.selectMyProductByCartId(productIds);
        System.out.println(list);
    }
    //测试加入购物车
    @Test
    public void t9(){
        boolean b = cartService.insertIntoCartByProdcutId(1, 1);
    }
    //测试Object->List
    @Test
    public void t10(){
        List<Product> list = cartService.getCartListByUserId(1);
        System.out.println("----------------------------------------------");
        System.out.println(list);
        System.out.println("----------------------------------------------");
        Object o=list;
        List list1=(List)o;
        System.out.println(list1);
        System.out.println("----------------------------------------------");

    }
}
