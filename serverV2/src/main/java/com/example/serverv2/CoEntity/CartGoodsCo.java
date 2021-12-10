package com.example.serverv2.CoEntity;


import com.example.serverv2.pojo.Cart;
import com.example.serverv2.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartGoodsCo {
    //下面是Goods的内容
    private Integer gid;
    private String gname;
    private Integer sid;
    private double gprice;
    private String gdescribe;
    private String gparagraph;
    private double gdiscount;
    private String gcata;
    private String gcover;
    //下面是Cart的内容
    private Integer cartid;
    private Integer num;
    private Integer uid;
    public void setByGoodsCart(Goods goods, Cart cart){
        assert Objects.equals(goods.getGid(), cart.getGid());
        this.gid = goods.getGid();
        this.gname = goods.getGname();
        this.sid = goods.getSid();
        this.gprice = goods.getGprice();
        this.gdescribe = goods.getGdescribe();
        this.gparagraph = goods.getGparagraph();
        this.gdiscount = goods.getGdiscount();
        this.gcata = goods.getGcata();
        this.gcover =goods.getGcover();
        //cart
        this.num = cart.getNum();
        this.uid = cart.getUid();
        this.cartid = cart.getCartid();
    }
}
