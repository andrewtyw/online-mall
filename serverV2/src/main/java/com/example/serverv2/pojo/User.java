package com.example.serverv2.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class User {
//    @TableId(type = IdType.AUTO)  //设置主键自增  注意! 数据库的这个字段也是要自己去设置自增的
//    private Integer uid;
//    private String name;
//    private Integer age;
//    private String email;
//    private Date createTime;
//    private Date updateTime;
//}
@TableName("user")
@Data
public class User {
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    private String uname;
    private String password;
    private String uemail;
    private String uaddress;
}