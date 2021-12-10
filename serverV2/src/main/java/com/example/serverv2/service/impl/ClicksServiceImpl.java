package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.ClicksMapper;
import com.example.serverv2.pojo.Clicks;
import com.example.serverv2.service.ClicksService;
import org.springframework.stereotype.Service;

@Service
public class ClicksServiceImpl extends ServiceImpl<ClicksMapper, Clicks> implements ClicksService {
}
