package com.example.serverv2.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("clicks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clicks {
    @TableId(value = "cid",type = IdType.AUTO)
    private Integer cid;
    private Integer sid;
    private Integer gid;
    private String gname;
    private Integer uid;
    private String uname;
    private String uemail;
    private Date ctime;
}
