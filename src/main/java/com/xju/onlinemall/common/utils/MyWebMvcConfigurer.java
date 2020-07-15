package com.xju.onlinemall.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    LoginHandlerInterceptor loginHandlerInterceptor;

    /**
     * 重定向,访问直接跳转首页,然后再被拦截检查是否以及登录
     *
     * */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


    /**
     *添加要拦截的请求和不需要拦截的请求
     * 以及静态资源不需要拦截,直接放行
     *
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/account.html","/login","/register.html","/userRegister").excludePathPatterns("/static/**")
        .excludePathPatterns("/assets/**").excludePathPatterns("/layuiadmin/**");

    }
}
