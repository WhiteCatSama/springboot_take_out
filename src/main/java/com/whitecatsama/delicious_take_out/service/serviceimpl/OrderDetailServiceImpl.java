package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.OrderDetail;
import com.whitecatsama.delicious_take_out.mapper.OrderDetailMapper;
import com.whitecatsama.delicious_take_out.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
