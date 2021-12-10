package com.example.serverv2.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.Seller;
import com.example.serverv2.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerController {
    private SellerService sellerService;
    @Autowired
    public void setSellerService(SellerService sellerService){
        this.sellerService = sellerService;
    }
    @PostMapping("/login")
    public Result<?> sellerLogin(@RequestBody Seller seller){
        LambdaQueryWrapper<Seller> wrapper  = new LambdaQueryWrapper<>();
        wrapper.eq(Seller::getSname,seller.getSname()).eq(Seller::getPassword,seller.getPassword());
        Seller seller1 = sellerService.getOne(wrapper);
        if(seller1==null){
            return Result.error("-1","用户名或密码错误");
        }
        else {
            return Result.success(seller1);
        }
    }

    @RequestMapping("/register")
    public Result<?> sellerRegister(@RequestBody Seller seller){
        LambdaQueryWrapper<Seller> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Seller::getSname,seller.getSname());
        Seller res = sellerService.getOne((wrapper));
        if(res==null){
            boolean effect = sellerService.saveOrUpdate((seller));
            if(effect){
                return Result.success();
            }
            else {
                return Result.error("-2","用户名无重复但注册失败");
            }
        }
        else {
            return Result.error("-1","用户名重复");
        }
    }

}
