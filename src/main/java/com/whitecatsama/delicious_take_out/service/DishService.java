package com.whitecatsama.delicious_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.Dish;
import com.whitecatsama.delicious_take_out.dto.DishDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DishService extends IService<Dish> {
    public boolean saveWhitFlavor(DishDto dishDto);
}
