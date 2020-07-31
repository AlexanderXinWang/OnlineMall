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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    public String wishlist(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        Integer userId = user.getUserId();
        List<Star> starList = starService.getStarByUserId(userId);
        List<Product> starProductList = new ArrayList<>();
        for (Star star:starList) {
            Product starProduct = new Product();
            starProduct = productService.selectByProductId(star.getProductId());
            starProductList.add(starProduct);
        }
        model.addAttribute("starProductList",starProductList);
        return "views_front/wishlist";
    }


    @RequestMapping("/getstars")
    @ResponseBody
    public Object getStars(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10")int pageSize, HttpSession session){
        User user = (User)session.getAttribute("user");
        session.setAttribute("USERID",user.getUserId());
        StarExample starExample = new StarExample();
        starExample.createCriteria().andUserIdEqualTo(user.getUserId());
        List<Star> stars = starService.selectByMultiExample(user.getUserId());
        return Result.success(starService.findStars(pageNo,pageSize,user.getUserId()),"分页 查询star 对象");
    }
    @RequestMapping("/addStar")
    @ResponseBody
    public Object addStar(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = (User)session.getAttribute("user");
        Integer product_id = Integer.parseInt(request.getParameter("productId"));
        Integer star_id = null;
        Byte star_is_delete = 0;
        if(!(request.getParameter("starId")).equals("null")){
            star_id = Integer.parseInt(request.getParameter("starId"));
        }
        if(!(request.getParameter("starIsDelete")).equals("null")){
            star_is_delete = Byte.parseByte(request.getParameter("starIsDelete"));
        }

        Star star = new Star();

        if (star_is_delete!=3 && star_is_delete!=4){
            starService.addStar(product_id,user.getUserId());
        }
        else if (star_is_delete==3){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete4 = Byte.parseByte("4");
            star.setStarIsDelete(star_is_delete4);
            starService.updateByPrimaryKeySelective(star);
        }
        else if (star_is_delete==4){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete3 = Byte.parseByte("3");
            star.setStarIsDelete(star_is_delete3);
            starService.updateByPrimaryKeySelective(star);
        }
        try {
            response.sendRedirect("/single-product-simple.html?id="+(product_id.toString()));
        } catch (IOException e) {
            System.out.println("页面跳转出错！");
        }
        return "single-product-simple.html?id="+(product_id.toString());
    }
    @RequestMapping("/addStarByLittleButton")
    @ResponseBody
    public Object addStarByLittleButton(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = (User)session.getAttribute("user");
        Integer product_id = Integer.parseInt(request.getParameter("productId"));
        Integer star_id = null;
        Byte star_is_delete = 1;
        Object rstarId = request.getParameter("starId");
        Object rstarIsDelete = request.getParameter("starIsDelete");
//        if(rstarId!=null && !rstarId.equals("null") && !rstarId.equals("")){
        if(!rstarId.equals("null")){
            star_id = Integer.parseInt(request.getParameter("starId"));
        }
        if(!rstarIsDelete.equals("null")){
            star_is_delete = Byte.parseByte(request.getParameter("starIsDelete"));
        }

        Star star = new Star();

        if (star_is_delete!=3 && star_is_delete!=4){
            starService.addStar(product_id,user.getUserId());
        }
        else if (star_is_delete==3){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete4 = Byte.parseByte("4");
            star.setStarIsDelete(star_is_delete4);
            starService.updateByPrimaryKeySelective(star);
        }
        else if (star_is_delete==4){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete3 = Byte.parseByte("3");
            star.setStarIsDelete(star_is_delete3);
            starService.updateByPrimaryKeySelective(star);
        }
        if (request.getParameter("condition").equals("null")){
            try {
                response.sendRedirect("/product.html");
            } catch (IOException e) {
                System.out.println("页面跳转出错！");
            }
        }else{
            try {
                response.sendRedirect("/product.html?pageNo="+request.getParameter("pageNo")+"&pageSize="+request.getParameter("pageSize")+"&condition="+request.getParameter("condition")+"&product_cat="+request.getParameter("product_cat")+"&s="+request.getParameter("s"));
            } catch (IOException e) {
                System.out.println("页面跳转出错！");
            }
        }

        return "product.html";
    }
    @RequestMapping("/addStarInProductList")
    @ResponseBody
    public Object addStarInProductList(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = (User)session.getAttribute("user");
        Integer product_id = Integer.parseInt(request.getParameter("productId"));
        Integer star_id = null;
        Byte star_is_delete = 1;

        if(!request.getParameter("starId").equals("null")){
            star_id = Integer.parseInt(request.getParameter("starId"));
        }
        if(!request.getParameter("starIsDelete").equals("null")){
            star_is_delete = Byte.parseByte(request.getParameter("starIsDelete"));
        }
        Star star = new Star();
        if (star_is_delete!=3 && star_is_delete!=4){
            starService.addStar(product_id,user.getUserId());
        }
        else if (star_is_delete==3){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete4 = Byte.parseByte("4");
            star.setStarIsDelete(star_is_delete4);
            starService.updateByPrimaryKeySelective(star);
        }
        else if (star_is_delete==4){
            star.setStarId(star_id);
            star.setProductId(product_id);
            Byte star_is_delete3 = Byte.parseByte("3");
            star.setStarIsDelete(star_is_delete3);
            starService.updateByPrimaryKeySelective(star);
        }
        if (request.getParameter("condition").equals("null")){
            try {
                response.sendRedirect("/product-list.html");
            } catch (IOException e) {
                System.out.println("页面跳转出错！");
            }
        }else{
            try {
                response.sendRedirect("/product-list.html?pageNo="+request.getParameter("pageNo")+"&pageSize="+request.getParameter("pageSize")+"&condition="+request.getParameter("condition")+"&cid="+request.getParameter("cid")+"&min="+request.getParameter("min")+"&max="+request.getParameter("max")+"&s="+request.getParameter("s"));
            } catch (IOException e) {
                System.out.println("页面跳转出错！");
            }
        }

        return "product-list.html";
    }
    @RequestMapping("/deleteStar")
    public Object delStar(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = (User)session.getAttribute("user");
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        List<Star> starId = starService.selectProductIdAndStarIdByPrimaryKey(productId,user.getUserId());
        starService.deleteStarById(starId.get(0).getStarId());
        try {
            response.sendRedirect("/wishlist.html");
        } catch (IOException e) {
            System.out.println("页面跳转出错！");
        }
        return "views_front/wishlist";
    }
    @DeleteMapping("/deleteStars")
    @ResponseBody
    public Object delStars(@RequestBody List<Integer> ids){
        starService.deleteStarsByIds(ids);
        return Result.success("批量删除成功");
    }
}
