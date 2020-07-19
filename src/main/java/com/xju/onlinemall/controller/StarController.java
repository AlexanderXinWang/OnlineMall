package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.Star;
import com.xju.onlinemall.common.domain.StarExample;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.common.utils.Result;
import com.xju.onlinemall.mapper.StarMapper;
import com.xju.onlinemall.service.ProductService;
import com.xju.onlinemall.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StarController {
    @Autowired
    StarService starService;
    @Autowired
    StarMapper starMapper;
    @Autowired
    ProductService productService;

    @RequestMapping("/wishlist.html")
    public String wishlist(HttpSession session, Model model, Integer userId){
        User user = (User)session.getAttribute("user");
        userId = user.getUserId();

        List<Star> starList = starService.getStarByUserId(userId);

        List<Product> starProductList = new ArrayList<>();

        for (Star star:starList) {
            Product starProduct = new Product();
            starProduct = productService.selectByProductId(star.getProductId());
            starProductList.add(starProduct);
        }

        System.out.println(starProductList);

        model.addAttribute("starProductList",starProductList);

        return "views_front/wishlist";
    }


    @RequestMapping("/getstars")
    @ResponseBody
    public Object getStars(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize, HttpSession session){
        User user = (User)session.getAttribute("user");
        session.setAttribute("USERID",user.getUserId());
//        System.out.println(user.getUserId());
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(user.getUserId());
        List<Star> stars = starMapper.selectByExample(starExample);
//        System.out.println(stars);
        return Result.success(starService.findStars(pageNo,pageSize,user.getUserId()),"分页 查询star 对象");
    }
    @DeleteMapping("/deleteStar")
    @ResponseBody
    public Object delStar(@RequestBody Product product, HttpSession session){
        User user = (User)session.getAttribute("user");
//        System.out.println(product);
//        System.out.println(product.getProductId());
        List<Star> starId = starMapper.selectProductIdAndStarIdByPrimaryKey(product.getProductId(),user.getUserId());
//        System.out.println(starId.get(0).getStarId());
        starService.deleteStarById(starId.get(0).getStarId());
        return Result.success("删除成功");
    }
    @DeleteMapping("/deleteStars")
    @ResponseBody
    public Object delStars(@RequestBody List<Integer> ids){
        starService.deleteStarsByIds(ids);
        return Result.success("批量删除成功");
    }
}
