package com.example.serverv2.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.Clicks;
import com.example.serverv2.pojo.Goods;
import com.example.serverv2.service.ClicksService;
import com.example.serverv2.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/clicks")
public class ClicksController {
    private ClicksService clicksService;
    private GoodsService goodsService;
    @Autowired
    public void setClicksService(ClicksService clicksService){
        this.clicksService = clicksService;
    }
    @Autowired
    public void setGoodsService(GoodsService goodsService){
        this.goodsService = goodsService;
    }

    @GetMapping("/findbysid{sid}")
    public Result<?> findBySid(@PathVariable Integer sid,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize)
    {
        LambdaQueryWrapper<Clicks> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Clicks::getSid,sid);
        Page<Clicks> page = new Page<>(pageNum, pageSize);
        Page<Clicks> clicksPage = clicksService.page(page,wrapper);
        if(clicksPage.getTotal()==0L){
            return Result.error("-1","没有此商家对应的用户浏览记录");
        }
        else {
            return Result.success(clicksPage);
        }

    }

    @PostMapping("/add")
    public Result<?> addClicks(@RequestBody Map<String, Object> map){
        Integer sid =Integer.parseInt(map.get("sid").toString());
        Integer gid = Integer.parseInt(map.get("gid").toString());
        Integer uid = Integer.parseInt(map.get("uid").toString());
        String uname = (String) map.get("uname");
        String gname = (String) map.get("gname");
        String uemail = (String) map.get("uemail");
        Goods goodsExisted = goodsService.getById(gid);
        if(goodsExisted==null|| !Objects.equals(goodsExisted.getGstates(), "在售")){
            //要保证这个gid是对应有商品的
            return Result.error("-1","无此商品");
        }
        else
        {
            Clicks clicks = new Clicks();
            clicks.setSid(sid);
            clicks.setGid(gid);
            clicks.setUid(uid);
            clicks.setUname(uname);
            clicks.setGname(gname);
            clicks.setUemail(uemail);
            boolean effect = clicksService.saveOrUpdate(clicks);
            return  effect?Result.success():Result.error("-2","新增失败");
        }



    }
}
