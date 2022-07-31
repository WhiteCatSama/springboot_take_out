package com.whitecatsama.delicious_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whitecatsama.delicious_take_out.domain.Employee;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EmployeeService extends IService<Employee> {

}
