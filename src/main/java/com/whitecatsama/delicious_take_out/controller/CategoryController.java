package com.whitecatsama.delicious_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import com.whitecatsama.delicious_take_out.domain.Category;
import com.whitecatsama.delicious_take_out.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Result addCategory(@RequestBody Category category){
        categoryService.save(category);
        return new Result(Code.SAVE_SUCCESS,"新增分类成功");
    }

    @GetMapping("/page")
    public Result page(int page,int pageSize,String name){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!=null,Category::getName,name);
        queryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,queryWrapper);
        return new Result(Code.GET_SUCCESS,pageInfo,"查询成功");
    }

    @DeleteMapping
    public Result deleteById(Long ids){

        if(categoryService.remove(ids)){
            return new Result(Code.DELETE_SUCCESS,"删除成功");
        }
        else
        {
            return new Result(Code.DELETE_FAILED,"已关联数据，删除失败");
        }

    }
    @PutMapping
    public Result editCategroy(@RequestBody Category category){
        categoryService.updateById(category);
        return new Result(Code.UPDATE_SUCCESS,"编辑成功");
    }
    @GetMapping("/list")
    public Result getCategoryList(Category category){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null, Category::getType,category.getType());
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
         List list = categoryService.list(queryWrapper);
        return new Result(Code.GET_SUCCESS,list,"菜品查询成功");
    }
}

