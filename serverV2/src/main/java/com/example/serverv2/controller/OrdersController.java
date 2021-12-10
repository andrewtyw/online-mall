package com.example.serverv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.Orders;
import com.example.serverv2.service.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private JavaMailSender javaMailSender;
    private OrdersService ordersService;
    private SellerService sellerService;
    private UserService userService;
    private CartService cartService;
    private GoodsService goodsService;
    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender){this.javaMailSender=javaMailSender;}
    @Autowired
    public void setCartService(CartService cartService){this.cartService = cartService;}
    @Autowired
    public void setGoodsService(GoodsService goodsService){this.goodsService = goodsService;}
    @Autowired
    public void setOrdersService(OrdersService ordersService){this.ordersService = ordersService;}
    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }
    @Autowired
    public void setSellerService(SellerService sellerService){
        this.sellerService = sellerService;
    }

    //根据店家的sid查询订单
    @GetMapping("/findbysid{sid}")
    public Result<?> findOrdersBySid(@PathVariable Integer sid,//这里的long会不会有问题?
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getSid,sid);
        Page<Orders> page = new Page<>(pageNum, pageSize);
        Page<Orders> sellerPage = ordersService.page(page,wrapper);
        if(sellerPage.getTotal()==0L){
            //说明根据这个sid根本找不到, 这个店家没有商品
            return Result.error("-1","此商家没有订单");
        }
        else {
            return Result.success(sellerPage);
        }
    }


    //根据用户的sid查询所有的订单
    @GetMapping("/findbyuid{uid}")
    public Result<?> findOrdersByUid(@PathVariable Integer uid,//这里的long会不会有问题?
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize){
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUid,uid);
        Page<Orders> page = new Page<>(pageNum, pageSize);
        Page<Orders> sellerPage = ordersService.page(page,wrapper);
        if(sellerPage.getTotal()==0L){
            //说明根据这个sid根本找不到, 这个店家没有商品
            return Result.error("-1","此用户没有订单");
        }
        else {
            return Result.success(sellerPage);
        }
    }

    //状态变更为 已发货
    @PutMapping("/makedeliver")
    public Result<?> makeDeliver(@RequestBody Orders orders){
        Integer id = orders.getOid();
        orders.setStage("已发货");
        boolean effect = ordersService.saveOrUpdate(orders);
//        Orders orders1 = ordersService.getById(id);
//        System.out.println(orders1.getStage());
        return effect?Result.success():Result.error("-1","变更为已发货失败");
    }

    //状态变更为已签收
    @PutMapping("/makesign")
    public Result<?> makeSign(@RequestBody Orders orders){
        orders.setStage("已签收");
        boolean effect = ordersService.saveOrUpdate(orders);
        return effect?Result.success():Result.error("-1","变更为已签收失败");
    }

    //状态变更为 未发货
    @PutMapping("/makeundeliver")
    public Result<?> makeUnDeliver(@RequestBody Orders orders){
        orders.setStage("未发货");
        boolean effect = ordersService.saveOrUpdate(orders);
        return effect?Result.success():Result.error("-1","变更为未发货失败");
    }

    //改变状态
    @PutMapping("/changstagebyid{oid}")
    public Result<?> changeStageById(@PathVariable Integer oid,
                                     @RequestBody Map<String, Object> map){
        String newStage = (String) map.get("newStage");
        Orders orders = ordersService.getById(oid);
        orders.setStage(newStage);
        boolean effect = ordersService.saveOrUpdate(orders);
        return effect?Result.success():Result.error("-1","变更失败");
    }

    @PostMapping("/email")
    public Result<?> sendEmail(@RequestBody Map<String, Object> map){
        String fromEmail = (String) map.get("fromEmail");
        String toEmail = (String) map.get("toEmail");
        String uname = (String) map.get("uname");
        String gname = (String) map.get("gname");
        String uaddress = (String) map.get("uaddress");
        String sname = (String) map.get("sname");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(sname+"-发货通知");
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSentDate(new Date());
        message.setText(uname+",我尊贵的客户, 您在本店购买的:"+gname+"已发货, 将在不久后送到您的地址:"+uaddress+", 请注意签收"+
                "\n\nBest Regards"+"\n"+sname);
        javaMailSender.send(message);
        return Result.success();
    }


}
