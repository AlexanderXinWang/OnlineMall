package com.xju.onlinemall;

import com.xju.onlinemall.common.domain.Admin;
import com.xju.onlinemall.common.domain.AdminExample;
import com.xju.onlinemall.common.domain.Book;
import com.xju.onlinemall.mapper.AdminMapper;
import com.xju.onlinemall.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnlinemallApplicationTests {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    AdminMapper mapper;

    @Test
    void contextLoads() {
        for (int i = 1; i <= 103; i++) {
            Book book = new Book();
            book.setAuthor("作者-"+i);
            book.setName("名字-"+i);
            book.setPrice(Math.random()*100);
            book.setRemark("备注信息-"+i);
            bookMapper.insert(book);
        }
    }
    @Test
    void t1() {
        AdminExample adminExample = new AdminExample();
        List<Admin> admins = mapper.selectByExample(adminExample);
        System.out.println(admins);
    }

}
