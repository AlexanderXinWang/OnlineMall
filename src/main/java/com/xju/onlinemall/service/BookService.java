package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BookService {
    public PageInfo<Book> findBooks(int pageNo, int pageSize);
    public void deleteBookById(Book book);

    void deleteBooksByIds(List<Integer> ids);
}
