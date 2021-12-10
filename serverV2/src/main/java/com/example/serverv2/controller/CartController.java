package com.example.serverv2.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serverv2.CoEntity.CartGoodsCo;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.*;
import com.example.serverv2.service.*;
import com.sun.mail.imap.ResyncData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {
    private OrdersService ordersService;
    private SellerService sellerService;
    private UserService userService;
    private CartService cartService;
    private GoodsService goodsService;
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

    //订单结算  提交订单
    @PostMapping("/submit{uid}")
    public Result<?> submitCart(@PathVariable Integer uid){
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUid,uid);//必须是还没有提交的订单
        int countExist = cartService.count(cartLambdaQueryWrapper);
        if(countExist==0){
            //说明用户不存在或者用户的购物车是空的
            return Result.error("-1","用户不存在或者用户的购物车是空的");
        }
        else {
            //把购物车中的信息加入到orders中
            List<Cart> cartList = cartService.list(cartLambdaQueryWrapper);//查询得到所有的用户对应的购物车记录
            List<Orders> ordersReadyToAdd = new ArrayList<>();
            List<Integer> batchRemoveIds = new ArrayList<>();
            int nullSeller = 0;
            int nullGoods = 0;
            int addNumFail = 0;
            for (Cart cart : cartList) {
                Goods goods = goodsService.getById(cart.getGid());//和cart对应的商品
                if(goods!=null&& Objects.equals(goods.getGstates(), "在售") && Objects.equals(cart.getGid(), goods.getGid())){
                    Seller seller = sellerService.getById(goods.getSid());
                    if(seller!=null){
                        //添加id
                        batchRemoveIds.add(cart.getCartid());
                        //添加到订单里
                        Orders orders = new Orders();
                        orders.setUid(uid);
//                    String cartUserName = userService.getById()
                        orders.setUname(userService.getById(uid).getUname());
                        orders.setUemail(userService.getById(uid).getUemail());
                        orders.setUaddress(userService.getById(uid).getUaddress());
                        orders.setGid(goods.getGid());
                        orders.setGname(goods.getGname());
                        orders.setSname(seller.getSname());
                        orders.setSid(goods.getSid());
                        orders.setOprice(cart.getNum()*goods.getGprice()*goods.getGdiscount());
                        orders.setGnum(cart.getNum());
                        ordersReadyToAdd.add(orders);
                        //更新销售量
                        goods.addSales(cart.getNum());
                        boolean effect0 = goodsService.saveOrUpdate(goods);
                        if(!effect0){addNumFail++;}

                    }
                    else {
                        nullSeller++;
                    }

                }
                else {
                    nullGoods++;
                }
            }
            ordersService.saveOrUpdateBatch(ordersReadyToAdd);
            boolean effect = cartService.removeByIds(batchRemoveIds);
            if(nullSeller!=0||nullGoods!=0||addNumFail!=0){
                Result<?> resultWarning =  Result.error("0","警告有"+nullGoods+"个找不到的goods"+nullSeller+"个找不到的seller"+addNumFail+"个商品销售额增加失败");
                return effect?resultWarning:Result.error("-2","删除原来购物车的内容失败");
            }
            else {
                return effect?Result.success():Result.error("-2","删除原来购物车的内容失败");
            }

        }
        //然后
        //最后标记用户对应的购物车中的cartorder字段为 oid
    }



   //给定uid, 查出所有的gid, 根据这一波的gid查询出所有的商品;
   // 总的来说就是根据uid查出购物车里面的所有商品,以及商品的购买数量
    @GetMapping("/findbyuid{uid}")
    public Result<?> findGoodsByUid(@PathVariable long uid,
                                    @RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize){
        LambdaQueryWrapper<Cart> cartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cartLambdaQueryWrapper.eq(Cart::getUid,uid);//必须是还没有提交的订单
        int countExist = cartService.count(cartLambdaQueryWrapper);
        if(countExist==0){
            //说明用户不存在或者用户的购物车是空的
            return Result.error("-1","用户不存在或者用户的购物车是空的");
        }
        else {
            List<Cart> cartList = cartService.list(cartLambdaQueryWrapper);//查询得到所有的用户对应的购物车记录
            List<CartGoodsCo> cartGoodsCoList = new ArrayList<>();
            int DoNotMacthCount = 0;
            for (Cart cart : cartList) {
                //首先根据某个gid查询出Goods
                Goods goods = goodsService.getById(cart.getGid());
                if(goods!=null&& Objects.equals(goods.getGstates(), "在售") && Objects.equals(cart.getGid(), goods.getGid())){//下架的筛掉

                    //然后插入到Co类中
                    CartGoodsCo cartGoodsCo = new CartGoodsCo();
                    cartGoodsCo.setByGoodsCart(goods,cart);
                    cartGoodsCoList.add(cartGoodsCo);
                }
                else {
                    DoNotMacthCount+=1;
                }
            }
            String info = "有"+DoNotMacthCount+"个商品已废弃";


            //得到了cartGoodsCoList, 接下来转换为Page
            Page<CartGoodsCo> cartGoodsCoPage = new Page<>(pageNum,pageSize);
            int count = cartGoodsCoList.size();
            List<CartGoodsCo> pageList = new ArrayList<>();
            int currId = pageNum>1 ? (pageNum-1)*pageSize:0;
            for (int i=0; i<pageSize && i<count - currId;i++){
                pageList.add(cartGoodsCoList.get(currId+i));
            }
            cartGoodsCoPage.setSize(pageSize);
            cartGoodsCoPage.setCurrent(pageNum);
            cartGoodsCoPage.setTotal(count);
            cartGoodsCoPage.setPages(count %pageSize == 0 ? count/pageSize :count/pageSize+1);
            cartGoodsCoPage.setRecords(pageList);
            Result<Page<CartGoodsCo>> result = new Result<>();
            result.setData(cartGoodsCoPage);
            result.setMsg(DoNotMacthCount==0?"成功":info);
            result.setCode("0");
            return result;
        }
    }

    //根据uid和gid增加购物车
    @PostMapping("/addcart")
    public Result<?> addCartByUidGid(@RequestBody Cart cart){
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid,cart.getUid()).eq(Cart::getGid,cart.getGid());

        //首先看看这个gid是否存在
        Goods IfGoodsExist = goodsService.getById(cart.getGid());
        User IfUserExist = userService.getById(cart.getUid());
        if (IfGoodsExist==null||IfUserExist==null){
            //说明不存在这个商品或者这个用户
            return Result.error("-3","新增的购物车的商品不存在或者用户id不存在");
        }
        else if (!Objects.equals(IfGoodsExist.getGstates(), "在售")){
            return Result.error("-3","商品已下架");
        }
        else{
            Cart cartExisted = cartService.getOne(wrapper);
            if(cartExisted==null){
                //说明里面没有这个记录, 可以直击加入

                boolean effect = cartService.saveOrUpdate(cart);
                if(effect){return Result.success();}
                else {return Result.error("-1","购物车内没有此记录但插入失败");}
            }
            else {
                //说明用户重复购买了
                cartExisted.addNum();
                boolean effect = cartService.saveOrUpdate(cartExisted);
                if(effect){return Result.success();}
                else {return Result.error("-2","购物车内数量新增失败");}
            }
        }

        //1,首先查询出购物车记录里面有没有这个 {uid gid}对应的购物车信息
        // 如果有则update数量, 数量+1即可
        // 如果没有, 则插入(数量为1)
    }

    //根据cartid来增加购物车数量
    @PutMapping("/updatecartnum")
    public Result<?> addCartById(@RequestBody Map<String, Object> map){
        Integer cartid =Integer.parseInt(map.get("cartid").toString());
        Integer num = Integer.parseInt(map.get("num").toString());
        Cart cart = cartService.getById(cartid);
        cart.setNum(num);
        boolean res = cartService.saveOrUpdate(cart);
        if(res){return Result.success();}
        else {return Result.error("-1","数量添加失败");}

    }

    //根据cartid删除记录
    @DeleteMapping("/deletebyid{cartid}")
    public Result<?> deleteCartById(@PathVariable Integer cartid){
        boolean effect = cartService.removeById(cartid);
        return  effect?Result.success():Result.error("-1","删除失败");
    }

    //根据uid查询商品总数
    @GetMapping("/cartnumbyuid{uid}")
    public Result<?> cartNumByUid(@PathVariable Integer uid){
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUid,uid);
        Integer count = cartService.count(wrapper);
        if (count!=0){
            List<Cart> cartList = cartService.list(wrapper);
            Integer sum = 0;
            for (Cart cart : cartList) {
                Goods goods = goodsService.getById(cart.getGid());
                if(Objects.equals(goods.getGstates(), "在售")){
                    sum+=cart.getNum();
                }

            }
            return Result.success(sum);
        }
        else {
            Integer num = 0;
            return Result.success(num);
        }


    }
}
