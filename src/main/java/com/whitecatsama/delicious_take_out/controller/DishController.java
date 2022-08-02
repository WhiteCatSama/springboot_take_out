package com.whitecatsama.delicious_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import com.whitecatsama.delicious_take_out.domain.Dish;
import com.whitecatsama.delicious_take_out.domain.DishFlavor;
import com.whitecatsama.delicious_take_out.dto.DishDto;
import com.whitecatsama.delicious_take_out.service.CategoryService;
import com.whitecatsama.delicious_take_out.service.DishFlavorService;
import com.whitecatsama.delicious_take_out.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public Result addDish(@RequestBody DishDto dishDto){
        dishService.saveWhitFlavor(dishDto);
        return new Result(Code.SAVE_SUCCESS,"新增菜品成功");
    }

    @GetMapping("/page")
    public Result page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> pageFinallyInfo = new Page<>();

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,pageFinallyInfo,"records");
        List<Dish> dishList = pageInfo.getRecords();
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish dish:dishList){
            DishDto dishDto =  new DishDto();
            BeanUtils.copyProperties(dish,dishDto);
            dishDto.setCategoryName(categoryService.getById(dish.getCategoryId()).getName());
            dishDtoList.add(dishDto);
        }
        pageFinallyInfo.setRecords(dishDtoList);
        return new Result(Code.GET_SUCCESS,pageFinallyInfo,"查询成功");
    }

    @GetMapping("/{dishId}")
    public Result getDishInfo(@PathVariable Long dishId){
        Dish dish = dishService.getById(dishId);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        dishDto.setCategoryName(categoryService.getById(dish.getCategoryId()).getName());
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId,dishId);
        dishDto.setFlavors(dishFlavorService.list(queryWrapper));
        return new Result(Code.GET_SUCCESS,dishDto,"查询成功");
    }
    @PutMapping
    public Result editDishInfo(@RequestBody DishDto dishDto){
        dishService.updateById((Dish)dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor:dishFlavors) {
            dishFlavor.setDishId(dishDto.getId());
        }
        dishFlavorService.saveBatch(dishFlavors);
        return new Result(Code.UPDATE_SUCCESS,"修改成功");
    }
    @DeleteMapping
    public Result deleteDish(String ids){
        //删除菜品
        String[] idArray = ids.split(",");
        List<String> resultList= new ArrayList<>(Arrays.asList(idArray));
        dishService.removeByIds(resultList);

        //删除相应口味

        for (String result:idArray) {
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,result);
            dishFlavorService.remove(queryWrapper);
        }
        return new Result(Code.DELETE_SUCCESS,"删除成功");
    }

    @PostMapping("/status/{status}")
    public Result setDishStatus(@PathVariable int status,String ids){
        String[] idArray = ids.split(",");
        if(status==0){
            for (String id:idArray) {
                LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(Dish::getStatus,0);
                updateWrapper.eq(Dish::getId,id);
                dishService.update(updateWrapper);
            }
            return new Result(Code.UPDATE_SUCCESS,"修改成功");
        }
        else{
            for (String id:idArray) {
                LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.set(Dish::getStatus,1);
                updateWrapper.eq(Dish::getId,id);
                dishService.update(updateWrapper);
            }
            return new Result(Code.UPDATE_SUCCESS,"修改成功");
        }

    }

    @GetMapping("/list")
    public Result getCategoryById(String categoryId){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,categoryId);
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> dishes = dishService.list(queryWrapper);
        return new Result(Code.GET_SUCCESS,dishes,"查询成功");
    }
}
