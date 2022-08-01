package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.DishFlavor;
import com.whitecatsama.delicious_take_out.mapper.DishFlavorMapper;
import com.whitecatsama.delicious_take_out.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
