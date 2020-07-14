package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.service.CartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 直接把该用户的购物车商品存在session中！！！！！ 名字是cartProducts
     *
     * 该方法是跳转购物车页面，并把商品信息传入
     **/

    @RequestMapping("/getCartList")
    public String getProducts(HttpSession session, ModelMap modelMap){
        //获得当前登录的用户
        Object user = session.getAttribute("user");
        //添加商品信息到modelMap中
        return "views_front/cart";
    }
    /**
     * 加入购物车的AJAX请求
     * 注意！ 前台必须用AJAX请求添加商品！
     * AJAX请求的数据name为productId
     *
     * 比如:
     * data:{"productId":productId},
     *
     * 用户id不用传！可以从session获取，count是添加到购物车中产品的数量,如果只是想要添加一个，可以不用传,如果要添加多个产品
     * 可以写成这样
     *  data:{"productId":productId,"count":count},
     *
     * 返回信息的key是msg
     **/

    @RequestMapping("/addProduct")
    @ResponseBody
    public Object addProject(HttpSession session, @RequestParam("productId")Integer productID,@Param("count") Integer count){
        //获得当前登录的用户信息
        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        Map<String,Object> map=new HashMap<>();
        boolean b=true;
        if (count==null || count==1){
            b = cartService.insertIntoCartByProdcutId(userId, productID);
        }
        else {
            for (int i=0;i<count;i++){
                b = cartService.insertIntoCartByProdcutId(userId, productID);
                if (b==false) break;
            }
        }

        //添加成功
        if (b){
            map.put("success",true);
            map.put("msg","添加成功");
            //把该用户的商品存进session中
            List<Product> cartListByUserId = cartService.getCartListByUserId(userId);
            session.setAttribute("cartProducts",cartListByUserId);
            return map;
        }
        else {
            System.out.println("添加失败");
            map.put("success",false);
            return map;
        }
    }
}
