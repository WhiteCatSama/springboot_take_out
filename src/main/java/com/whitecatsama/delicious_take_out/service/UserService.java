package com.whitecatsama.delicious_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService extends IService<User> {
}
