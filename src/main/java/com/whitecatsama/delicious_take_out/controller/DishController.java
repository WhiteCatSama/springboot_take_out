package com.whitecatsama.delicious_take_out.controller;

import com.whitecatsama.delicious_take_out.service.DishFlavorService;
import com.whitecatsama.delicious_take_out.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;

}
