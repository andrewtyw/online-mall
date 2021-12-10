package com.example.serverv2.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @TableId(value = "cartid",type = IdType.AUTO)
    private Integer cartid;
    private Integer uid;
    private Integer gid;
    private Integer num=1;
    public void addNum(){
        this.num++;
    }

}
