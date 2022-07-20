package com.reji.www.service.lmpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reji.www.entity.Employee;
import com.reji.www.mapper.EmployeeMapper;
import com.reji.www.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
