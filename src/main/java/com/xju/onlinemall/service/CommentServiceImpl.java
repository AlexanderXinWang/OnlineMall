package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Comment;
import com.xju.onlinemall.common.domain.CommentExample;
import com.xju.onlinemall.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 评论服务接口的实现类
 * 后期实现分页
 * */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    /**
     * 根据商品id获得评论,注意,评论表是单独的一个表,用户id和商品id请确保正确
     * */
    @Override
    public List<Comment> selectByProductId(Integer productId) {
        CommentExample commentExample = new CommentExample();

        //如果传入的参数为空,返回全部评论
        if (productId==null){
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            System.out.println("参数为空,返回全部结果");
            return comments;
        }
        //传入的参数不为空,查找指定的结果
        else{
            commentExample.createCriteria().andProductIdEqualTo(productId);
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            return comments;
        }
    }
    /**
     * 根据用户id获得评论,注意,评论表是单独的一个表,用户id和商品id请确保正确
     * */
    @Override
    public List<Comment> selectByUserId(Integer userId) {
        CommentExample commentExample = new CommentExample();

        //传入的参数为空,查找全部评论
        if (userId==null){
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            System.out.println("参数为空,返回全部结果");
            return comments;
        }
        //传入的参数不为空,查找指定的结果,如果查询不到结果,list的size为0
        else{
            commentExample.createCriteria().andUserIdEqualTo(userId);
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            return comments;
        }

    }
    /**
     * 根据用户id和商品id获得评论,注意,评论表是单独的一个表,用户id和商品id请确保正确
     * 如果一个为空,另一个不为空,或者都为空,都查找全部
     * */
    @Override
    public List<Comment> selectByUserIdAndProductId(Integer userId, Integer productId) {
        CommentExample commentExample = new CommentExample();

        //任何一个参数为空,都返回全部结果
        if (userId==null || productId ==null){
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            System.out.println("参数为空,返回全部结果");
            return comments;

        }
        else {
            System.out.println("根据用户id和商品id正常进行查询评论");
            commentExample.createCriteria().andUserIdEqualTo(userId).andProductIdEqualTo(productId);
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            return comments;
        }

    }
    /**
     * 添加用户评论,把用户的id和商品id确定的comment对象写入数据库
     * */
    @Override
    public boolean insertIntoCommentByComment(Comment comment) {
        if (comment==null){
            System.out.println("评论对象为空,插入失败");
            return false;
        }
        else if (comment.getProductId()==null || comment.getUserId()==null){
            System.out.println("评论对象的用户id或产品id为空,插入失败");
            return false;
        }
        else{
            int insert = commentMapper.insert(comment);
            return true;
        }
    }
    /**
     * 添加用户评论,细节注入,选择性输入对象
     * */
    @Override
    public boolean insertIntoCommentByDetails(Integer userId, Integer productId, Integer score, String context, Date date) {
        //判断关键字是否为空
        if (userId==null || productId==null){
            System.out.println("用户id或商品id不能为空,插入结果失败");
            return false;
        }
        //只要用户id和商品id不为空,那么其他的字段都能帮你自动补全
        else {
            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setProductId(productId);
            //默认10分
            if (score==null){
                System.out.println("打分字段自动补全！");
                score=10;
            }
            //默认好评
            if (context==null){
                System.out.println("评价字段自动补全！");
                context="该用户未作出评价,默认好评！！！！！";
            }
            //默认好评
            if (context==""){
                System.out.println("评价字段自动补全！");
                context="该用户未作出评价,默认好评！！！！！";
            }
            //默认日期
            if (date==null){
                System.out.println("评价日期字段自动补全！");
                date=new Date();
            }
            comment.setScore(score);
            comment.setContext(context);
            comment.setCommentTime(date);

            int insert = commentMapper.insert(comment);
            return true;
        }
    }
}
