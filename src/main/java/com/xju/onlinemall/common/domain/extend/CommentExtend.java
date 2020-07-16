package com.xju.onlinemall.common.domain.extend;

/**
 *
 * 扩展comment
 * */
public class CommentExtend {

    protected String username;

    protected Integer count;

    public String getUsername() {
        return username;
    }

    public CommentExtend setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public CommentExtend setCount(Integer count) {
        this.count = count;
        return this;
    }
}
