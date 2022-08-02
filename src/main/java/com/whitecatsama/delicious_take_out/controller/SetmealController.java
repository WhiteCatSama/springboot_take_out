package com.whitecatsama.delicious_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import com.whitecatsama.delicious_take_out.domain.Category;
import com.whitecatsama.delicious_take_out.domain.Setmeal;
import com.whitecatsama.delicious_take_out.domain.SetmealDish;
import com.whitecatsama.delicious_take_out.dto.SetmealDto;
import com.whitecatsama.delicious_take_out.service.CategoryService;
import com.whitecatsama.delicious_take_out.service.SetmealDishService;
import com.whitecatsama.delicious_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDto setmealDto){
        setmealService.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish:setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);
        return new Result(Code.SAVE_SUCCESS,"新增成功");
    }
    @GetMapping("/page")
    public Result getSetmealInfo(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> pageFinallyInfo = new Page<>();
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Setmeal::getName,name);
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo,queryWrapper);
        BeanUtils.copyProperties(pageInfo,pageFinallyInfo,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> lists = new ArrayList<>();
        for (Setmeal record:records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record,setmealDto);
            String categoryName = categoryService.getById(record.getCategoryId()).getName();
            if(categoryName!=null)
            {setmealDto.setCategoryName(categoryName);
            lists.add(setmealDto);}
        }
        pageFinallyInfo.setRecords(lists);
        return new Result(Code.GET_SUCCESS,pageFinallyInfo,"查询成功");
    }

    @DeleteMapping
    public Result deleteSetmeal(@RequestParam  ArrayList<Long> ids){
        setmealService.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(queryWrapper);
        return new Result(Code.DELETE_SUCCESS,"删除成功");
    }

    @PostMapping("/status/{status}")
    public Result editStatus(@PathVariable int status,@RequestParam ArrayList<Long> ids){
        log.info("status:",status);
        log.info("ids:",ids);
        if(status==0){
            LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(Setmeal::getId,ids);
            updateWrapper.set(Setmeal::getStatus,0);
            setmealService.update(updateWrapper);
        }
        else {
            LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.in(Setmeal::getId,ids);
            updateWrapper.set(Setmeal::getStatus,1);
            setmealService.update(updateWrapper);
        }
        return new Result(Code.UPDATE_SUCCESS,"修改成功");
    }
    @GetMapping("/{id}")
    public Result getSetmealInfo(@PathVariable Long id){
        Setmeal setmeal = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        setmealDto.setSetmealDishes( setmealDishService.list(queryWrapper));
        LambdaQueryWrapper<Category> queryCategoryWrapper = new LambdaQueryWrapper<>();
        queryCategoryWrapper.eq(Category::getId,setmeal.getCategoryId());
        setmealDto.setCategoryName(categoryService.getOne(queryCategoryWrapper).getName());
        return new Result(Code.GET_SUCCESS,setmealDto,"查询成功");
    }

    @PutMapping
    public Result editSetmealInfo(@RequestBody SetmealDto setmealDto){
        setmealService.updateById((Setmeal)setmealDto);

        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(queryWrapper);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish:setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());

        }
        setmealDishService.saveBatch(setmealDishes);

        return new Result(Code.UPDATE_SUCCESS,"修改成功");
    }
}
