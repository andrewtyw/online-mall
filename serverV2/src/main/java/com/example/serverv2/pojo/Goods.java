package com.example.serverv2.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @TableId(value = "gid",type = IdType.AUTO)
    private Integer gid;
    private String gname;
    private Integer sid;
    private String sname;
    private double gprice;
    private String gdescribe;
    private String gparagraph;
    private double gdiscount;
    private String gcata;
    private String gcover;
    private String gstates = "在售";
    private Integer gsales = 0;
    public void addSales(int num){
        this.gsales+=num;
    }
}
