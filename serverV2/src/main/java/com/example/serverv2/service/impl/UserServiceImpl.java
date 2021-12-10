package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.UserMapper;
import com.example.serverv2.pojo.User;
import com.example.serverv2.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
