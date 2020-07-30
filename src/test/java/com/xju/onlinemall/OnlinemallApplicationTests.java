package com.xju.onlinemall;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xju.onlinemall.common.domain.*;
import com.xju.onlinemall.common.domain.extend.pCountCName;
import com.xju.onlinemall.mapper.*;
import com.xju.onlinemall.service.CartService;
import com.xju.onlinemall.service.CommentServiceImpl;
import com.xju.onlinemall.service.OrderService;
import com.xju.onlinemall.service.UserService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class OnlinemallApplicationTests {
    @Autowired
    CategoryMapper mapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    SystemLogMapper systemLogMapper;
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartService cartService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @Test
    public void t1(){
        List<Category> categories = mapper.selectByExample(new CategoryExample());
        System.out.println(categories);
        List<String> categoryNames = new ArrayList<>();
        for (Category category: categories) {
            categoryNames.add(category.getCategoryName());
        }
        String first = categoryNames.get(0);
        System.out.println(first);
    }
    //测试获取用户
    @Test
    public void t2(){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo("user1").andPasswordEqualTo("123");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users.size());
    }
    //测试插入日志信息
    @Test
    public void t3(){
        SystemLog systemLog = new SystemLog();
        //日志中要有id为1的用户
        systemLog.setUserId(1);
        systemLog.setOperation("登录操作");
        systemLog.setLevel(1);
        systemLog.setCreateTime(new Date());
        systemLogMapper.insert(systemLog);
    }
    //测试商品插入功能,全部属性正常时
    @Test
    public void t4(){
        Comment comment = new Comment();
        comment.setUserId(1);
        comment.setProductId(1);
        comment.setScore(10);
        comment.setContext("商品评价是指生产厂家、商家或者消费者根据具体商品的性能、规格、材质、使用寿命、外观等商品的内在价值设定一个可量化或定性的评价体系，由消费者对商品使用价值进行评价的过");
        comment.setCommentTime(new Date());
        boolean b = commentService.insertIntoCommentByComment(comment);
        System.out.println(b);
    }
    //测试商品插入功能,comment为空或用户id或产品id为空时，测试结果执行成功
    @Test
    public void t5(){
        Comment comment = new Comment();
        //不设置用户id
        comment.setUserId(1);
        //不设置商品id
//        comment.setProductId(1);
        comment.setScore(10);
        comment.setContext("商品评价是指生产厂家、商家或者消费者根据具体商品的性能、规格、材质、使用寿命、外观等商品的内在价值设定一个可量化或定性的评价体系，由消费者对商品使用价值进行评价的过");
        comment.setCommentTime(new Date());
        //设置comment为空时
        /**
         * 评论对象为空,插入失败
         * false
         * */
//        comment=null;
        boolean b = commentService.insertIntoCommentByComment(comment);
        System.out.println(b);
    }
    //测试商品插入功能,默认设置是否生效
    @Test
    public void t6(){
        boolean b = commentService.insertIntoCommentByDetails(1,1,null,"",null);
        System.out.println(b);
    }
    //测试查询
    @Test
    public void t7(){
//        List<Comment> comments = commentService.selectByUserId(null);
//        List<Comment> comments = commentService.selectByProductId(null);
//        List<Comment> comments = commentService.selectByUserIdAndProductId(null, 1);
        List<Comment> comments = commentService.selectByUserIdAndProductId(1, 1);
        System.out.println(comments);
    }
    //测试根据购物车id,查询商品
    @Test
    public void t8(){
        int userId =1;
        Byte isDelete = 3;
        List<Cart> carts = cartMapper.selectByUserIdAndIsDelete(userId,isDelete);
        System.out.println(carts);
        List<Integer> productIds = new ArrayList<>();
        for (Cart cart: carts) {
            productIds.add(cart.getProductId());
        }
        List<Product> list = cartService.getCartListByUserId(1);
//        List<Product> list= cartMapper.selectMyProductByCartId(productIds);
        System.out.println(list);
    }
    //测试加入购物车
    @Test
    public void t9(){
        boolean b = cartService.insertIntoCartByProdcutId(1, 1);
    }
    //测试Object->List
    @Test
    public void t10(){
        List<Product> list = cartService.getCartListByUserId(1);
        System.out.println("----------------------------------------------");
        System.out.println(list);
        System.out.println("----------------------------------------------");
        Object o=list;
        List list1=(List)o;
        System.out.println(list1);
        System.out.println("----------------------------------------------");

    }
    //测试OrderServiceImpl能否生效
    @Test
    public void t11(){
        Integer userId=1;
        //获取某个用户的所有订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId);
        //List<Object> ordersList = orderMapper.selectById(userId);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        //注意List中的每一个Order都含有一个商品的List,每次循环下来都会产生一一个完整的订单
        for (Order order:orders){
            //获得该订单的商品id
            int productId=order.getProductId();
            //获得该订单的对应商品
            Product product=productMapper.selectByPrimaryKey(productId);
            /**
             * 把该订单的商品写入该订单对象的商品列表
             * */
            order.setProduct(product);
            //获取订单状态标记
            Byte status_num =order.getPayStatus();
            //获得订单对应状态
            String orderStatus=orderMapper.selectStatusByNum(status_num);
            //将订单状态写入订单对象
            order.setStatus(orderStatus);
            System.out.println(order.getUserId()+","+order.getOrderId()+","+order.getProduct().getProductName()+
                    ",数量×"+order.getOrderNumber()+",实付: ¥"+order.getPayMoney()+","+order.getStatus());
        }
        System.out.println(orders);
    }
    //测试修改后的购物车后端逻辑
    @Test
    public void t12(){
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(1);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        List<Product> list = null;
        if (carts.size()==0){
            list = null;
        } else if(carts.size()!=0){
            //获取商品id的列表
            List<Integer> productIds = new ArrayList<>();
            for (Cart cart: carts) {
                productIds.add(cart.getProductId());
            }
            //查询返回当前用户购物车里的所有商品
            list= cartMapper.selectMyProductByCartId(productIds);
            //设置每个商品的数量
            for (Product product:list){
                int count=0;
                Integer productId = product.getProductId();
                for(Integer single:productIds){
                    if (single==productId){
                        count++;
                    }
                }
                product.setCount(count);
            }
        }
        System.out.println(list);
    }

    @Autowired
    CategoryMapper categoryMapper;
    //批量生产产品类别和商品
    @Test
    public void t13(){
        for (int i=0;i<50;i++){
            Category category = new Category();
            category.setCategoryName("测试分类"+i);
            categoryMapper.insert(category);
        }
        for (int i=0;i<100;i++){
            Product product = new Product();
            product.setProductName("商品"+i);
            product.setProNo("2020"+i);
            product.setPrice((double) ((int) (Math.random()*1000)+10));
            product.setCount((int) (Math.random()*10));
            product.setAddTime(new Date());
            product.setCategoryId((int) (Math.random()*10)+11);
            product.setPkey("Pc"+i);
            product.setContext("非常好用"+i);
            productMapper.insert(product);
        }

    }
    @Test
    public void t14(){
        PageHelper.startPage(1,10);
        ProductExample productExample = new ProductExample();
        List<Product> list = productMapper.selectByExample(productExample);
        for (Product product:list){
            System.out.println(product.getProductName());
        }
        PageInfo<Product> objectPageInfo = new PageInfo<>(list);
    }

    //测试根据类别id查询商品
    @Test
    public void t15(){
        Integer categoryId = 2;
        List<Product> products = productMapper.selectByCategory(categoryId);
        System.out.println(products);
    }

    //测试根据用户id和商品id逻辑删除购物车中商品
    @Test
    public void t16(){
        cartMapper.logicDelete(1,1);
        List<Product> products=cartService.getCartListByUserId(1);
        System.out.println(products);
    }

    //测试后台登录
    @Test
    public void t17(){
        List<User> users = userService.selectUserByNameAndPassword("admin1", "123");
        System.out.println(users);
    }

    //测试订单存储
    @Test
    public void t18(){

        List<Order> orderList =new ArrayList<Order>();
        for (int i=0;i<3;i++){
            Order order=new Order();
            order.setUserId(1);  //下单用户
            order.setProductId(2+i);  //下单商品id
            order.setCreateTime(new Date());  //下单时间
            order.setPayStatus((byte)5);  //订单状态，下单后默认为5（已付款）
            order.setIsDelete((byte)3);  //逻辑删除，默认为3（未删除）
            order.setReceiver("菜是原罪");  //收货人姓名
            order.setAddress("CN 浙江省杭州市 西湖区 余杭塘路886号 浙江大学 邮编:310012");  //收货地址
            orderList.add(order);
        }
        String msg=orderService.saveOrders(orderList);
        System.out.println(msg);
    }

    //系统日志时间模糊查询
    @Test
    public void t19(){
        Integer logId = null;
        Integer userId = null;
        String time = null;
        String time2 = null;
        time = "2020-07-27 16:3";
        time = time.concat("%");
        System.out.println(time);
        List<SystemLog> systemLogs = systemLogMapper.selectByMyExample(logId,userId,time2);
        System.out.println(systemLogs);
    }
    //测试商品分类对应的商品数量
    @Test
    public void t20(){
        List<pCountCName> pCountCNames = productMapper.selectNumGroupByCategory(4);
        HashMap<String, pCountCName> map = new HashMap<>();
        for (int i=0;i<pCountCNames.size();i++){
            map.put("p"+i,pCountCNames.get(i));
        }
        System.out.println(map);
    }

    @Test
    public void t21(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put("value",12);
        map.put("name","han");

        System.out.println(map);
    }
}
