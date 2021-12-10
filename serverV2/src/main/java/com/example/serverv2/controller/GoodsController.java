package com.example.serverv2.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serverv2.common.Result;
import com.example.serverv2.pojo.Goods;
import com.example.serverv2.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/goods")
public class GoodsController {
    private GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService){
        this.goodsService = goodsService;
    }
    //0,找出所有的goods, 或者查询, 最终结果分页
    @GetMapping("/find")
    public Result<?> findGoods(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "100") Integer pageSize,
                               @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Goods::getGsales).eq(Goods::getGstates,"在售");
        if (StrUtil.isNotBlank(search)){
            wrapper.like(Goods::getGname,search);//如果传过来的search不是空, 就按照传过来的名字查
        }
        Page<Goods> page = new Page<>(pageNum, pageSize);
        Page<Goods> userPage = goodsService.page(page,wrapper);
        return Result.success(userPage);
    }

    //1, 根据商家的id来查询所有的goods
    @GetMapping("/findbysid{sid}")
    public Result<?> findById(@PathVariable Integer sid,//这里的long会不会有问题?
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getSid,sid);//.eq(Goods::getGstates,"在售") 这里也显示没有 在售的货物吧
        Page<Goods> page = new Page<>(pageNum, pageSize);
        Page<Goods> userPage = goodsService.page(page,wrapper);
        if(userPage.getTotal()==0L){
            //说明根据这个sid根本找不到, 这个店家没有商品
            return Result.error("-1","商家未添加商品");
        }
        else {
            return Result.success(userPage);
        }

    }






    //2, 根据gid查询出一条goods
    @GetMapping("/findbygid{gid}")
    public Result<?> finByGid(@PathVariable long gid){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getGid,gid).eq(Goods::getGstates,"在售");
        Goods goodsRes = goodsService.getOne(wrapper);
        if(goodsRes==null){
            return Result.error("-1","没有找到此gid对应的商品");
        }
        else {
            return Result.success(goodsRes);
        }
    }

    //3, 根据效率降序输出15个商品
    @GetMapping("/findgoodsales")
    public Result<?> findBest15sales(){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Goods::getGsales).eq(Goods::getGstates,"在售");
        List<Goods> goodsList = goodsService.list(wrapper);
        if(goodsList.size()<15){
            return Result.success(goodsList);
        }
        else {
            return Result.success(goodsList.subList(0,15));//只取前15个
        }
    }

    //4 商品的删除
    @DeleteMapping("/delete{gid}")
    public Result<?> deleteByGid(@PathVariable Integer gid){
        Goods goods = goodsService.getById(gid);
        goods.setGstates("下架");
        boolean effect = goodsService.saveOrUpdate(goods);
        return effect?Result.success():Result.error("-1","删除失败");
    }
    //5 商品的修改
    @PutMapping("/update")
    public Result<?>updateGoods(@RequestBody Goods goods){
        boolean effect = goodsService.saveOrUpdate(goods);
        return effect?Result.success():Result.error("-1","更新失败");
    }
}
