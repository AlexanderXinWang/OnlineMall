package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    @ResponseBody
    public List<Object> list(){  //查询用户购物车信息

        String username = null;
        username="MuShaoAi";
        List<Object> cartList=cartService.list(username);
        System.out.println(cartList);
        return cartList;
    }


}
