package com.whitecatsama.delicious_take_out.service.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.Orders;
import com.whitecatsama.delicious_take_out.mapper.OrderMapper;
import com.whitecatsama.delicious_take_out.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
}
