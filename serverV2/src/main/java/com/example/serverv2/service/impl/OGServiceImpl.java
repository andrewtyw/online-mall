package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.OGMapper;
import com.example.serverv2.pojo.OG;
import com.example.serverv2.service.OGService;
import org.springframework.stereotype.Service;

@Service
public class OGServiceImpl extends ServiceImpl<OGMapper, OG> implements OGService {
}
