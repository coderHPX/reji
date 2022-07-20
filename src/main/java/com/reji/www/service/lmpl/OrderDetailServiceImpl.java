package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.entity.OrderDetail;
import com.reji.www.mapper.OrderDetailMapper;
import com.reji.www.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}