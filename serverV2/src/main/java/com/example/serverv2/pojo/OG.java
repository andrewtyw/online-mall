package com.example.serverv2.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("og")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OG {
    @TableId(value = "ogid",type = IdType.AUTO)
    private Integer ogid;
    private Integer oid;
    private Integer gid;

}
