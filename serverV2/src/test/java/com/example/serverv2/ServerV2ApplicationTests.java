package com.example.serverv2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.serverv2.common.Result;
import com.example.serverv2.mapper.UserMapper;
import com.example.serverv2.pojo.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Map;

@MapperScan("com.example.serverv2.mapper")
@SpringBootTest
class ServerV2ApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    JavaMailSender javaMailSender;
    @Test
    void contextLoads() {
    }
    @Test
    void localDirTest(){
        System.out.println(System.getProperty("user.dir"));//C:\Users\24508\IdeaProjects\online-mall\serverV2
        //C:\Users\24508\IdeaProjects\online-mall\serverV2\src\main\resources\files   files的dir
    }
    @Test
    public void mapperTest(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Test
    public void sendEmail(){
        String fromEmail = "2450814202@qq.com";
        String toEmail = "isandrew.t.y.wong@gmail.com";
        String uname = "tywang";
        String gname = "iphone13";
        String uaddress = "Daring R.D.South";
        String sname = "GoGoMart自营店";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(sname+"-发货通知");
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSentDate(new Date());
        message.setText(uname+",我尊贵的客户, 您在本店购买的:"+gname+"已发货, 将在不久后送到您的地址:"+uaddress+", 请注意签收"+
                "\n\nBest Regards"+"\n"+sname);
        javaMailSender.send(message);
    }



}
