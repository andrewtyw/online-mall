package com.example.serverv2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.serverv2.pojo.User;
import org.springframework.stereotype.Repository;

@Repository //代表持久层 ??
public interface UserMapper extends BaseMapper<User> {  //这里会帮你提供 泛型的 CRUD操作
    //写完意味着所有的CRUD操作都完成了
}
