package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.GoodsMapper;
import com.example.serverv2.pojo.Goods;
import com.example.serverv2.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
