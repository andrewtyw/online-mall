package com.example.serverv2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serverv2.mapper.SellerMapper;
import com.example.serverv2.pojo.Seller;
import com.example.serverv2.service.SellerService;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {
}
