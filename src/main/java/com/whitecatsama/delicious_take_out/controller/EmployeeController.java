package com.whitecatsama.delicious_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import com.whitecatsama.delicious_take_out.domain.Employee;
import com.whitecatsama.delicious_take_out.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return new Result(Code.LOGIN_FAILED, "用户名不存在");
        }
        if (!emp.getPassword().equals(password)) {
            return new Result(Code.LOGIN_FAILED, "密码错误");
        }
        if (emp.getStatus() == 0) {
            return new Result(Code.LOGIN_FAILED, "账号已禁用");
        }
        request.getSession().setAttribute("employee", emp.getId());
        return new Result(Code.LOGIN_SUCCESS, emp, "登录成功");
    }

    @PostMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return new Result(Code.LOGOUT_SUCCESS, "退出成功");
    }

    @PostMapping
    public Result addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return new Result(Code.SAVE_SUCCESS, "添加员工成功");
    }

    @GetMapping("/page")
    public Result page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        Page pageInfo = new Page(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo,queryWrapper);
        return new Result(Code.GET_SUCCESS,pageInfo,"查询成功");
    }

    @PutMapping
    public Result update(HttpServletRequest request,@RequestBody Employee employee) {
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return new Result(Code.UPDATE_SUCCESS,"员工信息修改成功");
    }
    @GetMapping("/{id}")
    public Result getInfoById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return new Result(Code.GET_SUCCESS,employee,"查询成功");
    }

}
