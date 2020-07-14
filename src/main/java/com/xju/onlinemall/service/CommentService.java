package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Comment;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

/**
 * 评论服务接口
 * */
public interface CommentService {
    /**
     * 根据商品id获得评论,查询结果为列表
     * */
    public List<Comment> selectByProductId(Integer productId);
    /**
     * 根据用户id获得评论,查询结果为列表
     * */
    public List<Comment> selectByUserId(Integer userId);
    /**
     * 根据用户id和商品id获得评论,查询结果为列表
     * */
    public List<Comment> selectByUserIdAndProductId(Integer userId,Integer productId);
    /**
     * 添加用户评论,把用户的id和商品id确定的comment对象写入数据库
     * */
    public boolean insertIntoCommentByComment(Comment comment);
    /**
     * 添加用户评论,细节注入,选择性输入对象
     * */
    public boolean insertIntoCommentByDetails(Integer userId, Integer productId,Integer grade ,String context, Date date);
}
