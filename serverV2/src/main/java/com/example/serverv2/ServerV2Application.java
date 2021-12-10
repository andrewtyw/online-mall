package com.example.serverv2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.serverv2.mapper")
@SpringBootApplication
public class ServerV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServerV2Application.class, args);
        System.out.println("后台启动完成");
    }

}
