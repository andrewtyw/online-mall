package com.example.serverv2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("orders")
@Data
public class Orders {
    @TableId(value = "oid",type = IdType.AUTO)
    private Integer oid;
    private Integer uid;
    private String uname;
    private String uemail;
    private String uaddress;
    private Integer gid;
    private String gname;
    private Integer sid;
    private double oprice;
    private Date otime;
    private String stage="未发货";
    private Integer gnum;
    private String sname;
}
