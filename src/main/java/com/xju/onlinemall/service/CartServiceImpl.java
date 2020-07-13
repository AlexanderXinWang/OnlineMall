package com.xju.onlinemall.service;

import com.xju.onlinemall.common.domain.Cart;
import com.xju.onlinemall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Object> list(String username) {
        return cartMapper.selectCartList(username);
    }
}
