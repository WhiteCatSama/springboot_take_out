package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.Category;
import com.whitecatsama.delicious_take_out.domain.Dish;
import com.whitecatsama.delicious_take_out.domain.Setmeal;
import com.whitecatsama.delicious_take_out.mapper.CategoryMapper;
import com.whitecatsama.delicious_take_out.service.CategoryService;
import com.whitecatsama.delicious_take_out.service.DishService;
import com.whitecatsama.delicious_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public boolean remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if(dishCount>0||setmealCount>0){
            return false;
        }
        else
        {
            super.removeById(id);
            return true;
        }

    }
}
