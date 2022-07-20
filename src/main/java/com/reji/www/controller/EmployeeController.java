package com.reji.www.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reji.www.common.Result;
import com.reji.www.entity.Employee;
import com.reji.www.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    static final String PASSWORD = "123456";

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request,  @RequestBody Employee employee){

        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(employeeLambdaQueryWrapper);

        if (null == one){
            return Result.error("请检查用户名！");
        }
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());


        if (!one.getPassword().equals(password)){
            return Result.error("密码错误!");
        }

        if (one.getStatus()==0){
            return Result.error("该用户被锁定!");
        }

        request.getSession().setAttribute("user",one);

        return Result.success(one);
    }


    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        request.removeAttribute("user");
        return Result.success(null);

    }


    @PostMapping()
    public Result saveEmployee(HttpServletRequest request, @RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex(PASSWORD.getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Employee user = (Employee) request.getSession().getAttribute("user");
//
//        employee.setCreateUser(user.getId());
//        employee.setUpdateUser(user.getId());

        employeeService.save(employee);

        return Result.success(employee);
    }


    @GetMapping("/page")
    public Result page(@RequestParam int page,@RequestParam int pageSize, @RequestParam(required = false) String name){
        Page<Employee> employeePage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.like(StringUtils.isNotBlank(name),Employee::getName,name);

        Page<Employee> page1 = employeeService.page(employeePage, employeeLambdaQueryWrapper);
        return Result.success(page1);
    }

    @GetMapping("/{id}")
    public Result queryEmployee(@PathVariable Long id){
        Employee byId = employeeService.getById(id);
        return Result.success(byId);

    }

    @PutMapping
    public Result update(HttpServletRequest request,@RequestBody  Employee employee){
//        Employee user = (Employee) request.getSession().getAttribute("user");
//        employee.setUpdateUser(user.getId());
//        employee.setUpdateTime(LocalDateTime.now());

        boolean b = employeeService.updateById(employee);
        if (b){
            return Result.success(null);
        }
        return Result.error("更新失败！");
    }

}
