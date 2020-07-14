package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.common.domain.CartExample;
import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.mapper.CartMapper;
import com.xju.onlinemall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Object> list(String username) {
        return cartMapper.selectCartList(username);
    }

    //获得当前用户的id购物车内所有的商品
    @Override
    public List<Product> getCartListByUserId(Integer userId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        List<Product> list = null;
        if (carts.size()==0){
            list = null;
        } else if(carts.size()!=0){
            list = null;
            List<Integer> productIds = new ArrayList<>();
            for (Cart cart: carts) {
                productIds.add(cart.getProductId());
            }
            //查询返回当前用户购物车里的所有商品
            list= cartMapper.selectMyProductByCartId(productIds);
        }
        return list;
    }

    @Override
    public boolean insertIntoCartByProdcutId(Integer userId, Integer prodectId) {
        if (userId ==null || prodectId == null){
            System.out.println("用户id或商品id为空,无法添加购物车");
            return false;
        }
        else{
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(prodectId);
            cartMapper.insert(cart);
            return true;
        }


    }

}
