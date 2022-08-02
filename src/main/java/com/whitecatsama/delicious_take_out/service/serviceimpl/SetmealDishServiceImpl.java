package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.SetmealDish;
import com.whitecatsama.delicious_take_out.mapper.SetmealDishMapper;
import com.whitecatsama.delicious_take_out.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
