package com.xju.onlinemall.controller;

import com.xju.onlinemall.common.domain.Product;
import com.xju.onlinemall.common.domain.User;
import com.xju.onlinemall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 跳转购物车,并传递用户商品数据
     * */
    @RequestMapping("/cart.html")
    public String cart(HttpSession session, ModelMap modelMap){
        //从session中获取当前登录用户购物车商品内的商品
        //从session中获取当前登录用户购物车商品内的商品
        List<Product> cartProducts=new ArrayList<Product>();
        Double amount=0.00; //结算商品总金额
        if (session.getAttribute("cartProducts")!=null){
            cartProducts = (List<Product>)session.getAttribute("cartProducts");
            for(Object products : cartProducts){
                Product product=(Product)products;
                amount=amount+product.getPrice();
            }
        }
        modelMap.addAttribute("cardProductsList",cartProducts);
        modelMap.addAttribute("amount",amount);
        return "views_front/cart";
    }
    /**
     * 通过用户id获得购物车内所有的product
     * 将购物车对应的所有的product的信息以JSON格式传入到前端
     * 直接把该用户的购物车商品存在session中！！！！！ 名字是cartProducts
     *
     * 该方法是跳转购物车页面，并把商品信息传入
     **/

    //下面代码由于过滤器和session的设置,请在/cart.html里写

//    @RequestMapping("/cart.html")
//    public String cart(HttpSession session, ModelMap modelMap){
//        //获取购物车商品内的商品
//        List cartProducts = (List)session.getAttribute("cartProducts");
//        modelMap.addAttribute("cardProductsList",cartProducts);
//        return "views_front/cart";
//    }
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

    @RequestMapping("/addProductToCart")
//    @ResponseBody
    public String addProject(HttpSession session, ModelMap modelMap,
                             HttpServletRequest request,Integer count){
        //获得当前登录的用户信息
        User user = (User) session.getAttribute("user");
        Integer userId = user.getUserId();
        Integer productId=Integer.parseInt(request.getParameter("id"));
        Map<String,Object> map=new HashMap<>();
        boolean b=true;
        if (count==null || count==1){
            b = cartService.insertIntoCartByProdcutId(userId, productId);
        }
        else {
            for (int i=0;i<count;i++){
                b = cartService.insertIntoCartByProdcutId(userId, productId);
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
            //return map;
        }
        else {
            System.out.println("添加失败");
            map.put("success",false);
            //return map;
        }
        List<Product> cartProducts=new ArrayList<Product>();
        Double amount=0.00; //结算商品总金额
        if (session.getAttribute("cartProducts")!=null){
            cartProducts = (List<Product>)session.getAttribute("cartProducts");
            for(Object products : cartProducts){
                Product product=(Product)products;
                amount=amount+product.getPrice();
            }
        }
        int cartCount=cartProducts.size();
        modelMap.addAttribute("cardProductsList",cartProducts);
        modelMap.addAttribute("headerCartProductList",cartProducts);
        modelMap.addAttribute("amount",amount);
        modelMap.addAttribute("cartCount",cartCount);

        return "views_front/cart";
    }

    /**
     * 购物车内商品的逻辑删除
     * */
    @RequestMapping("/deleteFromCart")
    public String deleteFromCart(HttpSession session, ModelMap modelMap,
                                 HttpServletRequest request){
        //获取当前登录用户信息
        User user=new User();
        user=(User) session.getAttribute("user");
        String msg=null;
        Integer userId = user.getUserId();//获取用户id
        //获取要删除的商品id
        Integer productId=Integer.parseInt(request.getParameter("id"));
        System.out.println(productId);
        msg=cartService.logicDelete(userId,productId);
        System.out.println(msg);
        //获取删除操作后的购物车商品信息
        List<Product> cartListByUserId = cartService.getCartListByUserId(user.getUserId());
        int cartCount=cartListByUserId.size();
        //逻辑删除后，更新session中的缓存
        session.setAttribute("cartProducts",cartListByUserId);
        //session缓存信息更新在本次跳转后，此次向前台传递新购物车商品信息，做假同步
        modelMap.addAttribute("cardProductsList",cartListByUserId);
        modelMap.addAttribute("headerCartProductList",cartListByUserId);
        modelMap.addAttribute("cartCount",cartCount);
        return "views_front/cart";
    }

    /**
     * 在header的购物车中删除商品
     */
    @RequestMapping("/deleteFromHeaderCart")
    public String delStars(HttpServletRequest request, HttpSession session, ModelMap modelMap){
        //获取当前登录用户信息
        User user=new User();
        user=(User) session.getAttribute("user");
        Integer userId = user.getUserId();
        Integer productId =  Integer.parseInt(request.getParameter("productId"));
        cartService.deleteByUserIdAndProductId(userId,productId);
        List<Product> cartListByUserId = cartService.getCartListByUserId(user.getUserId());
        int cartCount=cartListByUserId.size();

        //逻辑删除后，更新session中的缓存
        session.setAttribute("cartProducts",cartListByUserId);
        //session缓存信息更新在本次跳转后，此次向前台传递新购物车商品信息，做假同步
        modelMap.addAttribute("cardProductsList",cartListByUserId);
        modelMap.addAttribute("headerCartProductList",cartListByUserId);
        modelMap.addAttribute("cartCount",cartCount);
        return "views_front/cart";
    }

    /**
     * 跳转结算页面，向结算页面传递结算商品信息，
     * */
    @RequestMapping("/checkout.html")
    public String checkout(HttpSession session, HttpServletRequest request,ModelMap modelMap){
        String[] productIds=request.getParameterValues("input_productId");
        String[] productNumber=request.getParameterValues("input_productNumber");
        String[] payMoney=request.getParameterValues("input_payMoney");
        String input_amount2=request.getParameter("input_amount2");
        String input_amount3=request.getParameter("input_amount3");
        //从session中获取当前登录用户购物车商品内的商品
        List<Product> cartProducts=new ArrayList<Product>();
        if (session.getAttribute("cartProducts")!=null){
            cartProducts = (List<Product>)session.getAttribute("cartProducts");
            for(Object products : cartProducts){
                Product product=(Product)products;
                for (int i =0;i<productIds.length;i++){
                    Integer pId=Integer.valueOf(productIds[i]);
                    if (product.getProductId()==pId){
                        product.setProductNumber(Integer.valueOf(productNumber[i]));
                        product.setPayMoney(Double.parseDouble(payMoney[i]));
                    }
                }

            }
        }
        Double amount2=Double.parseDouble(input_amount2);
        Double amount3=Double.parseDouble(input_amount3);
        modelMap.addAttribute("productsCheckout",cartProducts);
        modelMap.addAttribute("amount2",amount2);
        modelMap.addAttribute("amount3",amount3);
        return "views_front/checkout";
    }

    /**
     * 从header结算按钮跳转结算页面，向结算页面传递结算商品信息，
     * */
    @RequestMapping("/headerCheckout.html")
    public String headerCheckout(HttpSession session, HttpServletRequest request,ModelMap modelMap){
        //从session中获取当前登录用户购物车商品内的商品
        List<Product> cartProducts=new ArrayList<Product>();
        Double amount=0.00; //结算商品总金额
        if (session.getAttribute("cartProducts")!=null){
            cartProducts = (List<Product>)session.getAttribute("cartProducts");
            for(Object products : cartProducts){
                Product product=(Product)products;
                product.setPayMoney(product.getPrice());
                product.setProductNumber(1);
                amount=amount+product.getPrice();
            }
        }

        modelMap.addAttribute("productsCheckout",cartProducts);
        modelMap.addAttribute("amount2",amount);
        modelMap.addAttribute("amount3",amount);
        return "views_front/checkout";
    }
}
