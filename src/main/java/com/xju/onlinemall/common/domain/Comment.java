package com.xju.onlinemall.common.domain;

import com.xju.onlinemall.common.domain.extend.CommentExtend;

import java.util.Date;

public class Comment extends CommentExtend {
    private Integer commentId;

    private String context;

    private Integer score;

    private Date commentTime;

    private Byte isDelete=0;

    private Integer userId;

    private Integer productId;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", context='" + context + '\'' +
                ", score=" + score +
                ", commentTime=" + commentTime +
                ", isDelete=" + isDelete +
                ", userId=" + userId +
                ", productId=" + productId +
                '}';
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}