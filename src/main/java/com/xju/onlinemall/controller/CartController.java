package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
    /**
     * 通过用户id获得购物车内所有的product
     * 将购物车对应的所有的product的信息以JSON格式传入到前端
     **/

    @RequestMapping("/getCartList")
    @ResponseBody
    public Object getProducts(HttpSession session){
        //获得当前登录镀锡
        Object user = session.getAttribute("user");

        return null;
    }
}
