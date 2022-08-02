package com.whitecatsama.delicious_take_out.dto;


import com.whitecatsama.delicious_take_out.domain.Setmeal;
import com.whitecatsama.delicious_take_out.domain.SetmealDish;
import lombok.Data;

import java.util.List;


@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
