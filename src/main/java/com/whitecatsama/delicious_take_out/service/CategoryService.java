package com.whitecatsama.delicious_take_out.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.Category;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryService extends IService<Category> {
    public boolean remove(Long id);
}
