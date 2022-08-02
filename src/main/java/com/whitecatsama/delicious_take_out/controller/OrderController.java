package com.whitecatsama.delicious_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import com.whitecatsama.delicious_take_out.domain.Orders;
import com.whitecatsama.delicious_take_out.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public Result getOrderPageInfo(int page, int pageSize, String number, String beginTime,String endTime){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(number!=null,Orders::getNumber,number);
            queryWrapper.between(beginTime!=null&&endTime!=null,Orders::getOrderTime,beginTime,endTime);
            queryWrapper.orderByDesc(Orders::getOrderTime);
            orderService.page(pageInfo,queryWrapper);
        return new Result(Code.GET_SUCCESS,pageInfo,"查询成功");
    }

    @PutMapping
    public Result setStatus(@RequestBody Orders orders){
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper();
        updateWrapper.eq(Orders::getId,orders.getId());
        updateWrapper.set(Orders::getStatus,orders.getStatus());
        orderService.update(updateWrapper);
        return new Result(Code.UPDATE_SUCCESS,"修改成功");
    }
}
