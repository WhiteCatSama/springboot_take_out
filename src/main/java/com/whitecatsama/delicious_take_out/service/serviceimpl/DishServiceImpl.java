package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.Dish;
import com.whitecatsama.delicious_take_out.domain.DishFlavor;
import com.whitecatsama.delicious_take_out.dto.DishDto;
import com.whitecatsama.delicious_take_out.mapper.DishMapper;
import com.whitecatsama.delicious_take_out.service.DishFlavorService;
import com.whitecatsama.delicious_take_out.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    @Override
    public boolean saveWhitFlavor(DishDto dishDto) {
        this.save(dishDto);
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor:dishFlavors) {
            dishFlavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
        return true;
    }
}
