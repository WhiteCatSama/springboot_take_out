package com.whitecatsama.delicious_take_out.dto;

import com.whitecatsama.delicious_take_out.domain.Dish;
import com.whitecatsama.delicious_take_out.domain.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
