package com.example.serverv2.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@TableName("seller")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller {
    @TableId(value ="sid",type = IdType.AUTO)
    private Integer sid;
    private String sname;
    private String password;
    private String semail;
}
