package com.whitecatsama.delicious_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.DishFlavor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DishFlavorService extends IService<DishFlavor> {
}
