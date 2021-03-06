package com.xju.onlinemall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.xju.onlinemall.mapper"})
public class OnlinemallApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinemallApplication.class, args);
    }
    //这里配置静态资源文件的路径导包都是默认的直接导入就可以



}
