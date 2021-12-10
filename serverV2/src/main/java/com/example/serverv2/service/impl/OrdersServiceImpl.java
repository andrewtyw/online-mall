package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.OrdersMapper;
import com.example.serverv2.pojo.Orders;
import com.example.serverv2.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
