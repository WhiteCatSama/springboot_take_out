package com.whitecatsama.delicious_take_out.service.serviceimpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whitecatsama.delicious_take_out.domain.User;
import com.whitecatsama.delicious_take_out.mapper.UserMapper;
import com.whitecatsama.delicious_take_out.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
